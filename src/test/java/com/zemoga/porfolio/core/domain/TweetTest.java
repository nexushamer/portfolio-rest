package com.zemoga.porfolio.core.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class TweetTest {
    @Test
    public void Testing_Getter_Methods() {
        final Tweet tweet = new Tweet();

        tweet.getId();
        tweet.getText();
        tweet.getDetails();
    }

    @Test
    public void Should_Equal_Itself() {
        final Tweet tweet = new Tweet();
        final boolean result = tweet.equals(tweet);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final Tweet tweet = new Tweet();
        final boolean result = tweet.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final Tweet tweet = new Tweet();
        final boolean result = tweet.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final Tweet tweet = new Tweet();

        final int result1 = tweet.hashCode();
        final int result2 = tweet.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final Tweet tweet = new Tweet();
        tweet.setId("123213");
        tweet.setDetails(null);
        tweet.setText("in this i recommend you");
        final Tweet tweet2 = new Tweet("1","example of tweet",null);

        final int result1 = tweet.hashCode();
        final int result2 = tweet2.hashCode();

        assertNotEquals(result1, result2);
    }
}