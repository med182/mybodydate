package fr.mybodydate.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.api.services.StripePayementService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripePayementService stripePayementService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession() {
        String sessionId = stripePayementService.createCheckoutSession();
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);

        return ResponseEntity.ok(response);
    }
}
