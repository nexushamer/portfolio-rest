package com.zemoga.porfolio.external.gateways;

import com.zemoga.porfolio.adapters.gateways.Gateways;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.social.twitter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwitterGatewayTest {
    private Twitter twitter;
    private Gateways<List<Tweet>,String> twitterGateway;

    @Before
    public void SetUp(){
        twitter = mock(Twitter.class);
        twitterGateway = new TwitterGateway(twitter);
    }

    @Test(expected = InvalidDataException.class)
    public void whenConsumingTwitterAndTheUserIsNull(){
        twitterGateway.consumeService(null);
    }

    @Test(expected = InvalidDataException.class)
    public void whenConsumingTwitterAndTheUserIsEmpty(){
        twitterGateway.consumeService(null);
    }

    @Test
    public void whenConsumingTwitterAndTheApiReturnAnError(){
        when(twitter.timelineOperations()).thenThrow(InvalidMessageRecipientException.class);

        List<Tweet> tweets = twitterGateway.consumeService("usuario01");
        Assert.assertEquals(tweets.size(),0);
    }

    @Test
    public void whenConsumingTwitterAndTheResponseIsSuccess(){
        List<Tweet> tweetsFromApi = new ArrayList<>();
        tweetsFromApi.add(new Tweet(1,EMPTY,new Date(),EMPTY,EMPTY,1L,1,EMPTY,EMPTY));

        TimelineOperations timelineOperations = mock(TimelineOperations.class);
        when(timelineOperations.getUserTimeline(anyString())).thenReturn(tweetsFromApi);
        when(twitter.timelineOperations()).thenReturn(timelineOperations);

        List<Tweet> tweets = twitterGateway.consumeService("usuario01");
        Assert.assertTrue(tweets.size() > 0);
    }
}