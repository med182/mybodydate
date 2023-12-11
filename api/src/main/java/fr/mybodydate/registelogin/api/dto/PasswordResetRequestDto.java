package fr.mybodydate.registelogin.api.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {

    private String phoneNumber;
    private String username;
    private String oneTimePassword;

}
