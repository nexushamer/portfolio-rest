package com.zemoga.porfolio.external.presentation;

import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.core.services.ProfileService;
import com.zemoga.porfolio.external.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.IOException;

import static com.zemoga.porfolio.utils.StringMessages.*;

@Controller
@RequestMapping("/users")
@Validated
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final ProfileService profileService;

    @Autowired
    public UsersController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(path = "/{userId}/profile")
    @CrossOrigin
    public @ResponseBody
    Profile retrieveProfile(
            @Valid @NotBlank(message = USER_ID_REQUIRED) @PathVariable("userId") String userId) {
        return profileService.retrieveProfile(userId);
    }

    @GetMapping(path = "/{userId}/profile/picture")
    @CrossOrigin
    public ResponseEntity<byte[]> retrieveProfilePicture(@Valid @NotBlank(message = USER_ID_REQUIRED) @PathVariable("userId") String userId) {
        byte[] pictureBytes = profileService.retrievePictureFromProfile(userId);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(pictureBytes);
    }

    @PutMapping(path = "/{userId}/profile")
    @CrossOrigin
    public @ResponseBody
    Message updateProfile(@Valid
                          @NotBlank(message = USER_ID_REQUIRED) @PathVariable("userId") String userId,
                          @NotNull(message = USER_ID_REQUIRED) @RequestBody Profile profile) {
        boolean profileWasUpdated = profileService.updateProfile(profile);

        Message message = new Message();

        if (profileWasUpdated)
            message.setDescription(USER_UPDATE_OK);
        else
            message.setDescription(USER_UPDATE_FAILED);

        return message;
    }

    @RequestMapping(value = "/{userId}/profile/picture", method = RequestMethod.PATCH, produces = "application/json", consumes = "multipart/form-data")
    @CrossOrigin
    public @ResponseBody
    Message updatePictureFromProfile(@PathVariable("userId") String userId,
                                     @RequestParam("profilePicture") MultipartFile file) {
        LOGGER.info("Updating the picture from the profile");
        LOGGER.debug(file.getName());
        LOGGER.debug(file.getOriginalFilename());

        boolean profileWasUpdated = false;
        try {
            profileWasUpdated = profileService.updateProfilePicture(userId, file.getBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        Message message = new Message();

        if (profileWasUpdated)
            message.setDescription(USER_UPDATE_OK);
        else
            message.setDescription(USER_UPDATE_FAILED);

        return message;
    }

}
