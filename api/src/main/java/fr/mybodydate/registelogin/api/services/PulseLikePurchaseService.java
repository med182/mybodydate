package fr.mybodydate.registelogin.api.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.Purchase;
import fr.mybodydate.registelogin.api.repository.IPurchaseRepository;

@Service
public class PulseLikePurchaseService {

    @Autowired
    private StripePayementService stripePayementService;

    @Autowired
    private IPurchaseRepository purchaseRepository;

    public void processPulseLikePurchase(BigDecimal amount, String currency) {

        String checkoutSessionId = stripePayementService.createCheckoutSession();

        boolean paymentSuccess = stripePayementService.processPayment(checkoutSessionId);

        if (paymentSuccess) {
            Purchase purchase = new Purchase();
            purchase.setAmount(amount);
            purchase.setCurrency(currency);
            purchase.setProduct("Pulse-Like");

            purchaseRepository.save(purchase);

        } else {
            throw new RuntimeException("Le paiement a échoué.");
        }

    }

}
