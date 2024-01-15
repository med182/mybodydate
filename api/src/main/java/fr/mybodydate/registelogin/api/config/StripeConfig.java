package fr.mybodydate.registelogin.api.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

@Configuration
public class StripeConfig {

    @Value("${stripe.secretKey:default_value}")
    private String secretKey;

    public void init() {
        Stripe.apiKey = secretKey;
    }

}
