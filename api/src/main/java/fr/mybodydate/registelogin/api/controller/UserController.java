
package fr.mybodydate.registelogin.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import fr.mybodydate.registelogin.api.dto.OtpRequest;
import fr.mybodydate.registelogin.api.dto.OtpResponse;
import fr.mybodydate.registelogin.api.dto.OtpStatus;
import fr.mybodydate.registelogin.api.model.Subscription;
import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.UserRepository;
import fr.mybodydate.registelogin.api.services.TwilioOTPService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwilioOTPService twilioOTPService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("L'adresse email existe déjà", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            return new ResponseEntity<>("Le numéro de téléphone existe déjà.",
                    HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setPhoneNumber(user.getPhoneNumber());

        OtpResponse otpResponse = twilioOTPService.sendOTP(otpRequest).block();

        if (otpResponse != null && otpResponse.getStatus() == OtpStatus.DELIVRED) {
            userRepository.save(user);
            return new ResponseEntity<>("Utilisateur enregistré avec succès.", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Erreur lors de l'envoi de l'OTP de confirmation.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody OtpRequest otpRequest) {
        try {
            String userInputOtp = otpRequest.getOtp();
            String newPassword = otpRequest.getNewPassword();

            // Valider l'OTP pour la réinitialisation du mot de passe
            Mono<String> otpValidationResult = twilioOTPService.validatePasswordResetOTP(userInputOtp, otpRequest);

            String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();

            otpValidationResult.block(); // Blocage pour attendre le résultat de la validation OTP

            // Mettez à jour le mot de passe de l'utilisateur
            User user = userRepository.findByEmailOrPhoneNumber(key, key);
            if (user != null) {
                String hashedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(hashedPassword);
                userRepository.save(user);

                return new ResponseEntity<>("Mot de passe réinitialisé avec succès.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Erreur lors de la réinitialisation du mot de passe.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User oldUser;

        if (user.getEmail() != null) {
            oldUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        } else if (user.getPhoneNumber() != null) {
            oldUser = userRepository.findByEmailAndPassword(user.getPhoneNumber(), user.getPassword());
        } else {
            return new ResponseEntity<>("L'adresse email ou le numéro de téléphone doit etre fourni.",
                    HttpStatus.BAD_REQUEST);
        }

        if (oldUser != null && passwordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("L'identifiant ou le mot de passe est incorrect.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        WebClient webClient = WebClient.builder().build();

        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("code", code);
        tokenRequest.put("redirect_uri", "votre-uri-de-direction-google");
        tokenRequest.put("client_id", "votre-client-id-google");
        tokenRequest.put("client_secret", "votre-client-secret-google");

        Map<String, Object> responseMap = webClient.post()
                .uri("https://www.googleapis.com/oauth2/v4/token")
                .body(BodyInserters.fromValue(tokenRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        String accessToken = (String) responseMap.get("access_token");

        Map<String, Object> userInfo = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/login/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        WebClient webClient = WebClient.builder().build();

        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("code", code);
        tokenRequest.put("redirect_uri", "votre-uri-de-redirection-facebook");
        tokenRequest.put("client_id", "votre-app-id-facebook");
        tokenRequest.put("client_secret", "votre-app-secret-facebook");

        Map<String, Object> responseMap = webClient.post()
                .uri("https://graph.facebook.com/v13.0/oauth/access_token")
                .body(BodyInserters.fromValue(tokenRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        String accessToken = (String) responseMap.get("access_token");

        Map<String, Object> userInfo = webClient.get()
                .uri("https://graph.facebook.com/v13.0/me?access_token=" + accessToken + "&fields=id,email,name")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/login/linkedin")
    public ResponseEntity<?> loginWithLinkedIn(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        WebClient webClient = WebClient.builder().build();

        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("code", code);
        tokenRequest.put("redirect_uri", "votre-uri-de-redirection-linkedin");
        tokenRequest.put("client_id", "votre-client-id-linkedin");
        tokenRequest.put("client_secret", "votre-client-secret-linkedin");

        Map<String, Object> responseMap = webClient.post()
                .uri("https://www.linkedin.com/oauth/v2/accessToken")
                .body(BodyInserters.fromValue(tokenRequest))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        String accessToken = (String) responseMap.get("access_token");

        Map<String, Object> userInfo = webClient.get()
                .uri("https://api.linkedin.com/v2/me" + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            Subscription subscription = user.getSubscription();
            return new ResponseEntity<>(subscription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
        }
    }

}
