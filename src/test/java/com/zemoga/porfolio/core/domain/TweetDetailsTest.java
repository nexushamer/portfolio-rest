package com.zemoga.porfolio.core.domain;

import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class TweetDetailsTest {
    @Test
    public void Testing_Getter_Methods() {
        final TweetDetails tweetDetails = new TweetDetails();

        tweetDetails.getId();
        tweetDetails.getName();
        tweetDetails.getProfileImageUrl();
    }

    @Test
    public void Should_Equal_Itself() {
        final TweetDetails tweetDetails = new TweetDetails();
        final boolean result = tweetDetails.equals(tweetDetails);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final TweetDetails tweetDetails = new TweetDetails();
        final boolean result = tweetDetails.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final TweetDetails tweetDetails = new TweetDetails();
        final boolean result = tweetDetails.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final TweetDetails tweetDetails = new TweetDetails();

        final int result1 = tweetDetails.hashCode();
        final int result2 = tweetDetails.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final TweetDetails tweetDetails = new TweetDetails(EMPTY, EMPTY,EMPTY);
        tweetDetails.setProfileImageUrl("http://localhost:8080");
        tweetDetails.setName("user01");
        final TweetDetails tweetDetails2 = new TweetDetails();
        tweetDetails2.setId("12321321");

        final int result1 = tweetDetails.hashCode();
        final int result2 = tweetDetails2.hashCode();

        assertNotEquals(result1, result2);
    }
}