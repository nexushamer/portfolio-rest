package com.zemoga.porfolio.adapters.mappers;

import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = ProfileMapperTest.class)
    public static class SpringTestConfig {
    }

    @Autowired
    private ProfileMapper profileMapper;

    @Test
    public void whenTestingTheMapOfTheEntityToTheModel() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("sermendiarias@gmail.com");
        profileEntity.setNames("Sergio");
        profileEntity.setLastNames("Mendieta");

        Profile profile = profileMapper.mapEntityToModel(profileEntity);

        assertNotNull(profile);
        assertEquals(profileEntity.getUserId(), profile.getUserId());
        assertEquals(profileEntity.getNames(), profile.getNames());
        assertEquals(profileEntity.getLastNames(), profile.getLastNames());
    }

    @Test
    public void whenTestingTheMapOfTheEntityToTheModelAndTheEntityIsNull() {
        Profile profile = profileMapper.mapEntityToModel(null);

        assertNull(profile);
    }

    @Test
    public void whenTestingTheMapOfTheModelToTheEntity() {
        Profile profile = new Profile();
        profile.setUserId("sermendiarias@gmail.com");
        profile.setNames("Sergio");
        profile.setLastNames("Mendieta");

        ProfileEntity profileEntity = profileMapper.mapModelToEntity(profile);

        assertNotNull(profileEntity);
        assertEquals(profileEntity.getUserId(), profile.getUserId());
        assertEquals(profileEntity.getNames(), profile.getNames());
        assertEquals(profileEntity.getLastNames(), profile.getLastNames());
    }

    @Test
    public void whenTestingTheMapOfTheModelToTheEntityAndTheModelIsNull() {
        ProfileEntity profileEntity = profileMapper.mapModelToEntity(null);

        assertNull(profileEntity);
    }

}