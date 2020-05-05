package com.zemoga.porfolio.core.services.impl;

import com.zemoga.porfolio.adapters.exceptions.RecordNotFoundException;
import com.zemoga.porfolio.adapters.gateways.Gateways;
import com.zemoga.porfolio.adapters.mappers.ProfileMapper;
import com.zemoga.porfolio.adapters.stores.ProfileStore;
import com.zemoga.porfolio.adapters.stores.impl.ProfileStoreImpl;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import com.zemoga.porfolio.core.services.ProfileService;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import com.zemoga.porfolio.external.datasources.repositories.ProfileRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceImplTest {
    private ProfileService profileService;
    private ProfileStore profileStore;
    private ProfileRepository profileRepository;
    private Gateways gateway;

    @Spy
    private ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    @Before
    public void setUp() {
        profileRepository = mock(ProfileRepository.class);
        gateway = mock(Gateways.class);
        profileStore = new ProfileStoreImpl(profileRepository, profileMapper);
        profileService = new ProfileServiceImpl(profileStore, gateway);
    }

    @Test(expected = InvalidDataException.class)
    public void whenRetrievingTheUserProfileAndTheUserIdIsEmpty() {
        profileService.retrieveProfile(null);
    }

    @Test(expected = InvalidDataException.class)
    public void whenRetrievingTheUserProfileAndTheUserIdIsNull() {
        profileService.retrieveProfile(EMPTY);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenRetrievingTheUserProfileAndTheUserIdDoesNotExistAtTheDataSource() {
        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        final String userId = "user01@gmail.com";
        profileService.retrieveProfile(userId);
    }

    @Test
    public void whenRetrievingTheUserProfileTheResponseIsOk() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("user01@gmail.com");
        profileEntity.setNames("Pepe");
        profileEntity.setLastNames("Gonzales");

        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(profileEntity));
        when(gateway.consumeService(any())).thenReturn(new ArrayList<>());

        final String userId = "user01@gmail.com";
        final Profile profile = profileService.retrieveProfile(userId);
        assertNotNull(profile);
        assertEquals(profileEntity.getUserId(), profile.getUserId());
        assertEquals(profileEntity.getNames(), profile.getNames());
        assertEquals(profileEntity.getLastNames(), profile.getLastNames());
    }

    @Test(expected = InvalidDataException.class)
    public void whenUpdatingTheProfileAndTheProfileIsNull() {
        profileService.updateProfile(null);
    }

    @Test(expected = InvalidDataException.class)
    public void whenUpdatingTheProfileAndTheUserIdIsEmpty() {
        profileService.updateProfile(new Profile());
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenUpdatingTheProfileAndTheUserDoesNoExists() {
        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Profile profile = new Profile();
        profile.setUserId("123456");
        profileService.updateProfile(profile);
    }

    //updateProfilePicture
    @Test(expected = RecordNotFoundException.class)
    public void whenUpdateProfilePictureAndTheProfileIsNull() {
        profileService.updateProfilePicture(null,null);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenUpdateProfilePictureAndTheProfileIsEmpty() {
        profileService.updateProfilePicture(EMPTY, new byte[0]);
    }

    @Test
    public void whenUpdateProfilePictureAndTheDataIsOk() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("user01@gmail.com");
        profileEntity.setNames("Pepe");
        profileEntity.setLastNames("Gonzales");

        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(profileEntity));

        boolean result = profileService.updateProfilePicture("user01@gmail.com", "datos".getBytes());
        Assert.assertTrue(result);
    }

    @Test
    public void whenUpdatingTheProfileAndTheDataIsOk() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("user01@gmail.com");
        profileEntity.setNames("Pepe");
        profileEntity.setLastNames("Gonzales");
        profileEntity.setPicture("test".getBytes());

        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(profileEntity));

        Profile profile = new Profile();
        profile.setUserId("123456");
        profileService.updateProfile(profile);
    }

    @Test(expected = InvalidDataException.class)
    public void whenRetrievingPictureFromProfileAndTheUserIdIsNull() {
        profileService.retrievePictureFromProfile(null);
    }

    @Test(expected = InvalidDataException.class)
    public void whenRetrievingPictureFromProfileAndTheUserIdIsEmpty() {
        profileService.retrievePictureFromProfile(EMPTY);
    }

    @Test
    public void whenRetrievingPictureFromProfileAndThePictureIsReturnWell() {
        final String userId = "usuario@gmail.com";

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("user01@gmail.com");
        profileEntity.setNames("Pepe");
        profileEntity.setLastNames("Gonzales");
        profileEntity.setPicture("test".getBytes());

        when(profileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(profileEntity));

        byte[] picture = profileService.retrievePictureFromProfile(userId);
        assertNotNull(picture);
    }

}