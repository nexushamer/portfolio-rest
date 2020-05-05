package com.zemoga.porfolio.adapters.stores.impl;

import com.zemoga.porfolio.adapters.exceptions.RecordNotFoundException;
import com.zemoga.porfolio.adapters.mappers.ProfileMapper;
import com.zemoga.porfolio.adapters.stores.ProfileStore;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import com.zemoga.porfolio.external.datasources.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Optional;

import static com.zemoga.porfolio.utils.StringMessages.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ProfileStoreImpl implements ProfileStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileStoreImpl.class);
    private final ProfileRepository profileRepository;
    private final ProfileMapper mapper;

    @Autowired
    public ProfileStoreImpl(ProfileRepository profileRepository, ProfileMapper mapper) {
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean createProfile(Profile profile) {
        if (profile == null)
            throw new RecordNotFoundException(USER_ID_REQUIRED);

        ProfileEntity entity = mapper.mapModelToEntity(profile);

        try {
            profileRepository.save(entity);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return false;
    }

    @Override
    public Profile retrieveProfileFromStore(String userId) {
        final Optional<ProfileEntity> profileOptional = profileRepository.findById(userId);

        if (profileOptional.isPresent())
            return mapper.mapEntityToModel(profileOptional.get());
        else
            throw new RecordNotFoundException(USER_NOT_FOUND);
    }

    @Override
    public byte[] retrievePictureFromProfile(String userId) {
        final Optional<ProfileEntity> profileOptional = profileRepository.findById(userId);

        if (profileOptional.isPresent()) {
            return profileOptional.get().getPicture();
        } else
            throw new RecordNotFoundException(USER_NOT_FOUND);
    }


    @Override
    public boolean updateProfile(Profile profile) {
        final Optional<ProfileEntity> profileOptional = profileRepository.findById(profile.getUserId());

        if (profileOptional.isPresent()) {
            final ProfileEntity entity = validAndMapProfileToEntity(profile, profileOptional.get());

            try {
                profileRepository.save(entity);
                return true;
            } catch (PersistenceException e) {
                LOGGER.error(e.getMessage());
            }
        } else
            throw new RecordNotFoundException(USER_NOT_FOUND);

        return false;
    }

    @Override
    public boolean updatePictureProfile(String userId, byte[] bytesOfFile) {
        final Optional<ProfileEntity> profileOptional = profileRepository.findById(userId);

        if (profileOptional.isPresent()) {
            ProfileEntity entity = profileOptional.get();
            entity.setPicture(bytesOfFile);

            try {
                profileRepository.save(entity);
                return true;
            } catch (PersistenceException e) {
                LOGGER.error(e.getMessage());
            }
        } else
            throw new RecordNotFoundException(USER_NOT_FOUND);

        return false;
    }

    private ProfileEntity validAndMapProfileToEntity(Profile profile, ProfileEntity profileEntity) {
        if (!isBlank(profile.getNames()))
            profileEntity.setNames(profile.getNames());

        if (!isBlank(profile.getLastNames()))
            profileEntity.setLastNames(profile.getLastNames());

        if (!isBlank(profile.getExperienceSummary()))
            profileEntity.setExperienceSummary(profile.getExperienceSummary());

        return profileEntity;
    }
}
