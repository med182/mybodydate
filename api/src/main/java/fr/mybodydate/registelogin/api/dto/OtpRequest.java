package fr.mybodydate.registelogin.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {

    private String phoneNumber;
    private String email;

    private String otp; // Ajout du champ pour l'OTP
    private String newPassword;

}
