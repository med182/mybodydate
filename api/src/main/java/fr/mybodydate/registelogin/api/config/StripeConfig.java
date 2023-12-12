package fr.mybodydate.registelogin.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

@Configuration
public class StripeConfig {

    @Value("${stripe.apiKey.secret}")
    private String secretKey;

    @Bean
    public void init() {
        Stripe.apiKey = secretKey;
    }

}
