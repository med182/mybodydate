package fr.mybodydate.registelogin.api.services;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import fr.mybodydate.registelogin.api.config.TwilioConfig;
import fr.mybodydate.registelogin.api.dto.OtpStatus;
import fr.mybodydate.registelogin.api.dto.PasswordResetRequestDto;
import fr.mybodydate.registelogin.api.dto.PasswordResetResponseDto;
import reactor.core.publisher.Mono;

@Service
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    java.util.Map<String, String> optMap = new HashMap<>();

    public Mono<PasswordResetResponseDto> sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {

        PasswordResetResponseDto passwordResetResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String otp = generateOTP();
            String otpMessage = "Dear Customer, Your OTP is ##" +
                    otp + "##. Use this Passcode to complete your transaction. Thank you! ";
            Message message = Message
                    .creator(to, from,
                            otpMessage)

                    .create();
            optMap.put(passwordResetRequestDto.getUsername(), otp);
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.DELIVRED, otpMessage);
        } catch (Exception ex) {
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.FAILED, ex.getMessage());
        }
        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOTP(String userInputOtp, String username) {
        if (userInputOtp.equals(optMap.get(username))) {
            optMap.remove(username, userInputOtp);
            return Mono.just("Valid OTP please proceed with your transaction !");
        } else {
            return Mono.error(new IllegalArgumentException("Invalid otp please retry"));
        }
    }

    private String generateOTP() {
        return new DecimalFormat()

                .format(new Random().nextInt(999999));
    }

}
