package com.zemoga.porfolio.core.services.impl;

import com.zemoga.porfolio.adapters.gateways.Gateways;
import com.zemoga.porfolio.adapters.stores.ProfileStore;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.core.domain.TweetDetails;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import com.zemoga.porfolio.core.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zemoga.porfolio.utils.StringMessages.USER_ID_REQUIRED;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private final ProfileStore profileStore;
    private final Gateways<List<Tweet>, String> twitterGateway;

    @Autowired
    public ProfileServiceImpl(ProfileStore profileStore, Gateways<List<Tweet>, String> twitterGateway) {
        this.profileStore = profileStore;
        this.twitterGateway = twitterGateway;
    }

    @Override
    public Profile retrieveProfile(String userId) {
        LOGGER.info("retrieveProfile method");

        if (isBlank(userId))
            throw new InvalidDataException(USER_ID_REQUIRED);

        Profile profile = profileStore.retrieveProfileFromStore(userId);
        LOGGER.info("Consuming the tweeter api for get the activity of the user");
        List<Tweet> tweets = new ArrayList<>();

        try {
            tweets = twitterGateway.consumeService(profile.getTwitterUser());
        } catch (InvalidDataException e) {
            LOGGER.error(e.getDescription());
        }

        LOGGER.info("Processing the response for add the tweets to the profile");
        profile.setTweets(mapTweetsToModel(tweets));

        return profile;
    }

    @Override
    public byte[] retrievePictureFromProfile(String userId) {
        if (isBlank(userId))
            throw new InvalidDataException(USER_ID_REQUIRED);

        return profileStore.retrievePictureFromProfile(userId);
    }

    @Override
    public boolean updateProfile(Profile profile) {
        if (profile == null || isBlank(profile.getUserId()))
            throw new InvalidDataException(USER_ID_REQUIRED);

        return profileStore.updateProfile(profile);
    }

    @Override
    public boolean updateProfilePicture(String userId, byte[] bytesOfFile) {
        return profileStore.updatePictureProfile(userId, bytesOfFile);
    }

    private List<com.zemoga.porfolio.core.domain.Tweet> mapTweetsToModel(List<Tweet> tweets) {
        LOGGER.info("Mapping the twitter model to the portfolio model");
        return tweets.stream().map(tweet -> {
            com.zemoga.porfolio.core.domain.Tweet tweetModel = new com.zemoga.porfolio.core.domain.Tweet();

            tweetModel.setId(String.valueOf(tweet.getId()));
            tweetModel.setText(tweet.getText());
            TweetDetails details = new TweetDetails();
            if(tweet.getRetweetCount() > 0 && tweet.getRetweetedStatus() != null) {
                Tweet retweet = tweet.getRetweetedStatus();
                details.setId(String.valueOf(retweet.getId()));
                details.setName(retweet.getUser().getName());
                details.setProfileImageUrl(retweet.getProfileImageUrl());
            } else {
                details.setId(String.valueOf(tweet.getId()));
                details.setName(tweet.getUser().getName());
                details.setProfileImageUrl(tweet.getProfileImageUrl());
            }

            tweetModel.setDetails(details);
            return tweetModel;
        }).collect(Collectors.toList());
    }
}
