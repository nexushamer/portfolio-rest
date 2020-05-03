package com.zemoga.porfolio.external.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoga.porfolio.Application;
import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.core.services.ProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ProfileService profileService;

    @Test
    public void givenUsers_whenRetrieveTheProfile_thenStatus200()
            throws Exception {

        Profile profile = new Profile();
        profile.setUserId("sermendiarias@gmail.com");
        profile.setNames("pepe");
        given(profileService.retrieveProfile(Mockito.any())).willReturn(profile);

        mvc.perform(get("/users/sermendiarias@gmail.com/profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(profile.getUserId())))
                .andExpect(jsonPath("$.names", is(profile.getNames())));
        verify(profileService,
                VerificationModeFactory.times(1)).retrieveProfile(Mockito.anyString());
        reset(profileService);
    }

    @Test
    public void givenUsers_whenRetrieveTheProfilePicture_thenStatus200()
            throws Exception {

        byte[] picture = new byte[0];
        given(profileService.retrievePictureFromProfile(Mockito.any())).willReturn(picture);

        mvc.perform(get("/users/sermendiarias@gmail.com/profile/picture")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG));
        verify(profileService,
                VerificationModeFactory.times(1)).retrievePictureFromProfile(Mockito.anyString());
        reset(profileService);
    }

    @Test
    public void givenUsers_whenUpdatingTheProfile_thenStatus200()
            throws Exception {
        given(profileService.updateProfile(Mockito.any(Profile.class))).willReturn(true);

        final ObjectMapper mapper = new ObjectMapper();
        Profile profile = new Profile();
        profile.setUserId("sermendiarias@gmail.com");

        mvc.perform(put("/users/sermendiarias@gmail.com/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(profileService,
                VerificationModeFactory.times(1)).updateProfile(Mockito.any(Profile.class));
        reset(profileService);
    }

    @Test
    public void givenUsers_whenUpdatingTheProfileAndTheUpdateFailed()
            throws Exception {
        given(profileService.updateProfile(Mockito.any(Profile.class))).willReturn(false);

        final ObjectMapper mapper = new ObjectMapper();
        Profile profile = new Profile();
        profile.setUserId("sermendiarias@gmail.com");

        mvc.perform(put("/users/sermendiarias@gmail.com/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(profileService,
                VerificationModeFactory.times(1)).updateProfile(Mockito.any(Profile.class));
        reset(profileService);
    }

    @Test
    public void givenUsers_whenUpdatingTheProfilePicture_thenStatus200()
            throws Exception {
        given(profileService.updateProfilePicture(Mockito.anyString(), Mockito.any())).willReturn(true);

        MockMultipartFile file = new MockMultipartFile("profilePicture", "foo.txt",
                "application/json", "Foo data".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/users/sermendiarias@gmail.com/profile/picture");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        mvc.perform(builder.file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        verify(profileService,
                VerificationModeFactory.times(1))
                .updateProfilePicture(Mockito.anyString(), Mockito.any());
        reset(profileService);
    }

    @Test
    public void givenUsers_whenUpdatingTheProfilePictureAndTheUpdateThrowAnException()
            throws Exception {
        given(profileService.updateProfilePicture(Mockito.anyString(), Mockito.any())).willReturn(false);

        MockMultipartFile file = mock(MockMultipartFile.class);

        given(file.getName()).willReturn("profilePicture");
        given(file.getBytes()).willThrow(IOException.class);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/users/sermendiarias@gmail.com/profile/picture");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        mvc.perform(builder.file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        reset(profileService);
    }

}