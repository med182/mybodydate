package fr.mybodydate.registelogin.api.services;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import fr.mybodydate.registelogin.api.config.TwilioConfig;
import fr.mybodydate.registelogin.api.dto.OtpStatus;
import fr.mybodydate.registelogin.api.dto.OtpRequest;
import fr.mybodydate.registelogin.api.dto.OtpResponse;
import reactor.core.publisher.Mono;

@Service
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    private final Map<String, String> optMap = new HashMap<>();

    public Mono<OtpResponse> sendOTP(OtpRequest otpRequest) {

        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String otp = generateOTP();
            String otpMessage = "Votre code de vérification MyBodyDate est : " + otp;

            Message message = Message.creator(to, from, otpMessage).create();

            String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();

            optMap.put(key, otp);
            return Mono.just(new OtpResponse(OtpStatus.DELIVRED, "Code OTP envoyé avec succès."));
        } catch (Exception ex) {
            return Mono.just(new OtpResponse(OtpStatus.FAILED, "Échec de l'envoi de l'OTP."));
        }

    }

    public Mono<String> validateOTP(String userInputOtp, OtpRequest otpRequest) {
        String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();
        if (userInputOtp.equals(optMap.get(key))) {

            optMap.remove(key);
            return Mono.just("Code OTP valide. Vous pouvez procéder.");
        } else {
            return Mono.error(new IllegalArgumentException("Code OTP invalide. Veuillez réessayer."));
        }
    }

    public Mono<OtpResponse> sendPasswordResetOTP(OtpRequest otpRequest) {
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String otp = generateOTP();
            String otpMessage = "Votre code de réinitialisation de mot de passe MyBodyDate est : " + otp;

            Message message = Message.creator(to, from, otpMessage).create();
            String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();

            optMap.put(key + "_reset", otp);
            return Mono.just(new OtpResponse(OtpStatus.DELIVRED, "Code OTP de réinitialisation envoyé avec succès."));

        } catch (Exception e) {
            return Mono.just(new OtpResponse(OtpStatus.FAILED, "Échec de l'envoi de l'OTP de réinitialisation."));
        }
    }

    public Mono<String> validatePasswordResetOTP(String userInputOtp, OtpRequest otpRequest) {
        String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();
        key += "_reset";

        if (userInputOtp.equals(optMap.get(key))) {
            optMap.remove(key);
            return Mono.just(
                    "Code OTP de réinitialisation valide. Vous pouvez procéder à la réinitialisation du mot de passe.");
        } else {
            return Mono
                    .error(new IllegalArgumentException("Code OTP de réinitialisation invalide. Veuillez réessayer."));
        }
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(999999));
    }

    public Mono<Void> sendRecoveryCodeBySMS(String phoneNumber, String recoveryCode) {
        try {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String recoveryMessage = "Your MyBodyDate recovery code is: " + recoveryCode;

            Message.creator(to, from, recoveryMessage).create();

            return Mono.empty(); // Return an empty Mono since there is no specific result to return
        } catch (Exception ex) {
            return Mono.error(ex); // Return an error Mono in case of failure
        }
    }

    public Mono<OtpResponse> sendAccountDeleteOtp(OtpRequest otpRequest) {
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String otp = generateOTP();
            String otpMessage = "Votre code de suppression de compte MyBodyDate : " + otp;

            Message.creator(to, from, otpMessage).create();
            String key = otpRequest.getEmail() != null ? otpRequest.getEmail() : otpRequest.getPhoneNumber();

            optMap.put(key + "_delete_account", otp);
            return Mono
                    .just(new OtpResponse(OtpStatus.DELIVRED, "Code OTP de suppression de compte envoyé avec succès."));

        } catch (Exception e) {
            return Mono.just((new OtpResponse(OtpStatus.FAILED, "Echec de l'envoi de l'OTP de suppression de compte")));
        }
    }
}
