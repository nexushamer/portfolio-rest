package com.zemoga.porfolio.adapters.stores;

import com.zemoga.porfolio.core.domain.Profile;

public interface ProfileStore {
    boolean createProfile(Profile profile);
    Profile retrieveProfileFromStore(String userId);
    byte[] retrievePictureFromProfile(String userId);
    boolean updateProfile(Profile profile);
    boolean updatePictureProfile(String userId,byte[] bytesOfFile);
}
