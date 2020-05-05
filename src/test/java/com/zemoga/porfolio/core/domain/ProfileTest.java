package com.zemoga.porfolio.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class ProfileTest {
    @Test
    public void Should_Equal_Itself() {
        final Profile profile = new Profile();
        final boolean result = profile.equals(profile);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final Profile profile = new Profile();
        final boolean result = profile.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final Profile profile = new Profile();
        final boolean result = profile.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final Profile profile = new Profile("usuario@gmail.com", "pepe",
                "perez", "My experience is",
                null, EMPTY, EMPTY);

        final int result1 = profile.hashCode();
        final int result2 = profile.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final Profile profile = new Profile();
        profile.setTweets(new Object());
        profile.setPictureFileName("picture.jpg");
        final Profile profile2 = new Profile();
        profile2.setUserId("123212132131");

        final int result1 = profile.hashCode();
        final int result2 = profile2.hashCode();

        assertNotEquals(result1, result2);
    }

}