package fr.mybodydate.registelogin.api.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mybodydate.registelogin.api.services.PulseLikePurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PulseLikePurchaseService pulseLikePurchaseService;

    @PostMapping("/pulse-like")
    public ResponseEntity<String> purchasePulseLike(@RequestBody BigDecimal amount,
            @RequestBody String currency) {

        try {
            pulseLikePurchaseService.processPulseLikePurchase(amount, currency);
            return ResponseEntity.ok("Achat de Pulse-Like réussi.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'achat de Pulse-Like");
        }

    }

}
