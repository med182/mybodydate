package fr.mybodydate.registelogin.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripePayementService {

    @Value("${stripe.publishableKey}")
    private String publishableKey;

    public String createCheckoutSession() {
        try {
            Stripe.apiKey = "sk_test_...";
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/success")
                    .setCancelUrl("http://localhost:8080/cancel")
                    .build();

            Session session = Session.create(params);
            return session.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPublishableKey() {
        return publishableKey;
    }

}
