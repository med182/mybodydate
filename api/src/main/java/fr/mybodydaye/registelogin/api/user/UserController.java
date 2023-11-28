package fr.mybodydaye.registelogin.api.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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

        userRepository.save(user);
        return new ResponseEntity<>("Utilisateur enregistré avec succès.", HttpStatus.OK);
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

}
