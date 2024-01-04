package fr.mybodydate.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.mybodydate.api.dto.OtpRequest;
import fr.mybodydate.api.dto.OtpResponse;
import fr.mybodydate.api.services.TwilioOTPService;

@RequestMapping("/api")
@Controller
public class TwilioOtpController {
	
	private final TwilioOTPService twilioOtpService;

    @Autowired
    public TwilioOtpController(TwilioOTPService twilioOtpService) {
        this.twilioOtpService = twilioOtpService;
    }

    @PostMapping("/verificationNumero")
    public ResponseEntity<OtpResponse> verificationNumero(@RequestBody OtpRequest otpRequest) {
        OtpResponse otpResponse = twilioOtpService.verificationNumero(otpRequest);
        return ResponseEntity.ok(otpResponse);
    }
    
    @PostMapping("/verificationCheck")
    public ResponseEntity<OtpResponse> verificationCheck(@RequestBody OtpRequest otpRequest) {
        OtpResponse otpResponse = twilioOtpService.verificationCheck(otpRequest);
        return ResponseEntity.ok(otpResponse);
    }
}
