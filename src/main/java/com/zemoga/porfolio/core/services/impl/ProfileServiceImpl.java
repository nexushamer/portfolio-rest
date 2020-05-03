package com.zemoga.porfolio.core.services.impl;

import com.zemoga.porfolio.adapters.stores.ProfileStore;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import com.zemoga.porfolio.core.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zemoga.porfolio.utils.StringMessages.USER_ID_REQUIRED;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private final ProfileStore profileStore;

    @Autowired
    public ProfileServiceImpl(ProfileStore profileStore) {
        this.profileStore = profileStore;
    }

    @Override
    public Profile retrieveProfile(String userId) {
        LOGGER.info("retrieveProfile method");

        if (isBlank(userId))
            throw new InvalidDataException(USER_ID_REQUIRED);

        return profileStore.retrieveProfileFromStore(userId);
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
        return profileStore.updatePictureProfile(userId,bytesOfFile);
    }
}
