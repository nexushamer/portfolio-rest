package com.zemoga.porfolio.adapters.stores.impl;

import com.zemoga.porfolio.adapters.exceptions.RecordNotFoundException;
import com.zemoga.porfolio.adapters.mappers.ProfileMapper;
import com.zemoga.porfolio.adapters.stores.ProfileStore;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import com.zemoga.porfolio.external.datasources.repositories.ProfileRepository;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileStoreImplTest {
    private ProfileStore profileStore;
    private ProfileRepository profileRepository;
    private ProfileMapper mapper;

    @Before
    public void setUp() {
        profileRepository = mock(ProfileRepository.class);
        mapper = mock(ProfileMapper.class);
        this.profileStore = new ProfileStoreImpl(profileRepository, mapper);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenCreatingProfileAndTheProfileIsNull() {
        profileStore.createProfile(null);
    }

    @Test
    public void whenCreatingProfileAndTheSaveAtDBFail() {
        Profile profile = new Profile();
        profile.setUserId("usuario01@gmail.com");

        when(profileRepository.save(any())).thenThrow(PersistenceException.class);

        boolean result = profileStore.createProfile(profile);
        assertFalse(result);
    }

    @Test
    public void whenCreatingProfileAndTheCreationIsSuccess() {
        Profile profile = new Profile();
        profile.setUserId("usuario01@gmail.com");

        when(profileRepository.save(any())).thenReturn(new ProfileEntity());

        boolean result = profileStore.createProfile(profile);
        assertTrue(result);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenRetrievingTheProfileAndTheUserDoesNotExists() {
        when(profileRepository.findById(any())).thenReturn(Optional.empty());

        profileStore.retrieveProfileFromStore("user@gmail.com");
    }

    @Test
    public void whenRetrievingTheProfileAndTheRetrieveIsSuccess() {
        when(profileRepository.findById(any())).thenReturn(Optional.of(new ProfileEntity()));
        when(mapper.mapEntityToModel(any())).thenReturn(new Profile());

        Profile profile = profileStore.retrieveProfileFromStore("user@gmail.com");
        assertNotNull(profile);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenUpdatingPictureProfileAndTheUserDoesNotExists() {
        when(profileRepository.findById(any())).thenReturn(Optional.empty());

        profileStore.updatePictureProfile("user@gmail.com", new byte[0]);
    }

    @Test
    public void whenUpdatingPictureProfileAndTheSaveFail() {
        when(profileRepository.findById(any())).thenReturn(Optional.of(new ProfileEntity()));
        when(profileRepository.save(any())).thenThrow(PersistenceException.class);

        boolean result = profileStore.updatePictureProfile("user@gmail.com", new byte[0]);
        assertFalse(result);
    }

    @Test
    public void whenUpdatingPictureProfileAndTheSaveIsSuccess() {
        when(profileRepository.findById(any())).thenReturn(Optional.of(new ProfileEntity()));
        when(profileRepository.save(any())).thenReturn(new ProfileEntity());

        boolean result = profileStore.updatePictureProfile("user@gmail.com", new byte[0]);
        assertTrue(result);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenRetrievePictureFromProfileAndTheUserDoesNotExists(){
        when(profileRepository.findById(any())).thenReturn(Optional.empty());

        profileStore.retrievePictureFromProfile("ususario01@gmail.com");
    }

    @Test
    public void whenRetrievePictureFromProfileAndTheRetrieveIsSuccess(){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("ususario01@gmail.com");
        profileEntity.setPicture("Datos".getBytes());

        when(profileRepository.findById(any())).thenReturn(Optional.of(profileEntity));

        byte[] picture = profileStore.retrievePictureFromProfile("ususario01@gmail.com");
        assertNotNull(picture);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenUpdatePictureProfileAndTheUserIdIsEmpty(){
        when(profileRepository.findById(any())).thenReturn(Optional.empty());

        profileStore.updateProfile(new Profile());
    }

    @Test
    public void whenUpdatePictureProfileAndTheSaveFail(){
        when(profileRepository.findById(any())).thenReturn(Optional.of(new ProfileEntity()));
        when(profileRepository.save(any())).thenThrow(PersistenceException.class);

        boolean result = profileStore.updateProfile(new Profile());
        assertFalse(result);
    }

    @Test
    public void whenUpdatePictureProfileAndTheSaveIsSuccess(){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("usuario@mail.com");
        profileEntity.setExperienceSummary("");

        Profile profile = new Profile();
        profile.setUserId("usuario@mail.com");
        profile.setExperienceSummary("Arquitecto con experiencia en ");

        when(profileRepository.findById(any())).thenReturn(Optional.of(profileEntity));
        when(profileRepository.save(any())).thenReturn(profileEntity);

        boolean result = profileStore.updateProfile(profile);
        assertTrue(result);
    }

    @Test
    public void whenUpdatePictureProfileAndTheLastNamesWasUpdatedSuccessful(){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("usuario@mail.com");
        profileEntity.setLastNames("martinez");

        Profile profile = new Profile();
        profile.setUserId("usuario@mail.com");
        profile.setLastNames("Martinez");

        when(profileRepository.findById(any())).thenReturn(Optional.of(profileEntity));
        when(profileRepository.save(any())).thenReturn(profileEntity);

        boolean result = profileStore.updateProfile(profile);
        assertTrue(result);
    }

    @Test
    public void whenUpdatePictureProfileAndTheNamesWasUpdatedSuccessful(){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId("usuario@mail.com");
        profileEntity.setNames("martinez");

        Profile profile = new Profile();
        profile.setUserId("usuario@mail.com");
        profile.setNames("Martinez");

        when(profileRepository.findById(any())).thenReturn(Optional.of(profileEntity));
        when(profileRepository.save(any())).thenReturn(profileEntity);

        boolean result = profileStore.updateProfile(profile);
        assertTrue(result);
    }

}