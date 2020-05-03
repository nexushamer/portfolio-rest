package com.zemoga.porfolio.core.services;

import com.zemoga.porfolio.core.domain.Profile;

public interface ProfileService {
    Profile retrieveProfile(String userId);
    byte[] retrievePictureFromProfile(String userId);
    boolean updateProfile(Profile profile);
    boolean updateProfilePicture(String userId, byte[] fileBytes);
}