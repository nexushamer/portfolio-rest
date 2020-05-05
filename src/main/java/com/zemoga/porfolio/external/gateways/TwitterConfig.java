package com.zemoga.porfolio.external.gateways;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterConfig {
    @Value("${spring.social.twitter.appId}")
    private String consumerKey;

    @Value("${spring.social.twitter.appSecret}")
    private String consumerSecret;

    @Value("${spring.social.twitter.token}")
    private String accessToken;

    @Value("${spring.social.twitter.secret}")
    private String accessTokenSecret;

    @Bean
    public Twitter getTwtTemplate() {
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
