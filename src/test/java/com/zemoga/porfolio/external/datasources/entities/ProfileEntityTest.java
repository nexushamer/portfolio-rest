package com.zemoga.porfolio.external.datasources.entities;

import com.zemoga.porfolio.external.models.Message;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class ProfileEntityTest {
    @Test
    public void Testing_Getter_Methods() {
        final ProfileEntity profileEntity = new ProfileEntity();

        profileEntity.getPicture();
        profileEntity.getLastNames();
        profileEntity.getNames();
        profileEntity.getUserId();
        profileEntity.getExperienceSummary();
    }

    @Test
    public void Should_Equal_Itself() {
        final ProfileEntity profileEntity = new ProfileEntity();
        final boolean result = profileEntity.equals(profileEntity);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final ProfileEntity profileEntity = new ProfileEntity();
        final boolean result = profileEntity.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final ProfileEntity profileEntity = new ProfileEntity();
        final boolean result = profileEntity.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final ProfileEntity profileEntity = new ProfileEntity();

        final int result1 = profileEntity.hashCode();
        final int result2 = profileEntity.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("12312213");
        profileEntity.setExperienceSummary("My profile is ...");
        final ProfileEntity profileEntity2 = new ProfileEntity();
        profileEntity2.setUserId("12312213");
        profileEntity2.setPicture(new byte[0]);

        final int result1 = profileEntity.hashCode();
        final int result2 = profileEntity2.hashCode();

        assertNotEquals(result1, result2);
    }
}