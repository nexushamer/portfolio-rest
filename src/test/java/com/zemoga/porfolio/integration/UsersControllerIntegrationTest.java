package com.zemoga.porfolio.integration;

import com.zemoga.porfolio.Application;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import com.zemoga.porfolio.external.datasources.repositories.ProfileRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UsersControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProfileRepository profileRepository;

    @After
    public void resetDb() {
        profileRepository.deleteAll();
    }

    @Test
    public void givenUsers_whenRetrieveTheProfile_thenStatus200()
            throws Exception {

        createUserAndProfile("sermendiarias@gmail.com", "sergio");

        mvc.perform(get("/users/sermendiarias@gmail.com/profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.names", is("sergio")));
    }

    private void createUserAndProfile(String userId,String names){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserId(userId);
        profileEntity.setNames(names);

        profileRepository.save(profileEntity);
    }

}