package com.zemoga.porfolio.external.gateways;

import com.zemoga.porfolio.adapters.gateways.Gateways;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.zemoga.porfolio.utils.StringMessages.TWITTER_USER_ID_REQUIRED;

@Component
public class TwitterGateway implements Gateways<List<Tweet>,String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterGateway.class);
    private final Twitter twitter;

    @Autowired
    public TwitterGateway(Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public List<Tweet> consumeService(String request) {
        if(StringUtils.isBlank(request))
            throw new InvalidDataException(TWITTER_USER_ID_REQUIRED);

        LOGGER.info("ConsumeTwitter Service");
        List<Tweet> tweets = new ArrayList<>();

        try {
            tweets = twitter.timelineOperations().getUserTimeline(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return tweets;
    }
}
