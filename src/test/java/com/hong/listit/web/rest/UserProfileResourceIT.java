package com.hong.listit.web.rest;

import com.hong.listit.ListitApp;
import com.hong.listit.domain.UserProfile;
import com.hong.listit.repository.UserProfileRepository;
import com.hong.listit.service.UserProfileService;
import com.hong.listit.service.dto.UserProfileDTO;
import com.hong.listit.service.mapper.UserProfileMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = ListitApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserProfileResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMAIL_ENABLED = false;
    private static final Boolean UPDATED_EMAIL_ENABLED = true;

    private static final Boolean DEFAULT_SMS_ENABLED = false;
    private static final Boolean UPDATED_SMS_ENABLED = true;

    private static final Instant DEFAULT_LAST_LOGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LAST_LOGIN_IP = 1;
    private static final Integer UPDATED_LAST_LOGIN_IP = 2;

    private static final Boolean DEFAULT_IS_LOCKED = false;
    private static final Boolean UPDATED_IS_LOCKED = true;

    private static final Instant DEFAULT_LOCK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOCK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MAX_IMAGES = 20;
    private static final Integer UPDATED_MAX_IMAGES = 19;

    private static final Integer DEFAULT_VALID_DAYS = 90;
    private static final Integer UPDATED_VALID_DAYS = 89;

    private static final Boolean DEFAULT_IS_PRIVACY_ENABLED = false;
    private static final Boolean UPDATED_IS_PRIVACY_ENABLED = true;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .phone(DEFAULT_PHONE)
            .emailEnabled(DEFAULT_EMAIL_ENABLED)
            .smsEnabled(DEFAULT_SMS_ENABLED)
            .lastLoginDate(DEFAULT_LAST_LOGIN_DATE)
            .lastLoginIp(DEFAULT_LAST_LOGIN_IP)
            .isLocked(DEFAULT_IS_LOCKED)
            .lockDate(DEFAULT_LOCK_DATE)
            .maxImages(DEFAULT_MAX_IMAGES)
            .validDays(DEFAULT_VALID_DAYS)
            .isPrivacyEnabled(DEFAULT_IS_PRIVACY_ENABLED);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .phone(UPDATED_PHONE)
            .emailEnabled(UPDATED_EMAIL_ENABLED)
            .smsEnabled(UPDATED_SMS_ENABLED)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE)
            .lastLoginIp(UPDATED_LAST_LOGIN_IP)
            .isLocked(UPDATED_IS_LOCKED)
            .lockDate(UPDATED_LOCK_DATE)
            .maxImages(UPDATED_MAX_IMAGES)
            .validDays(UPDATED_VALID_DAYS)
            .isPrivacyEnabled(UPDATED_IS_PRIVACY_ENABLED);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();
        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserProfile.isEmailEnabled()).isEqualTo(DEFAULT_EMAIL_ENABLED);
        assertThat(testUserProfile.isSmsEnabled()).isEqualTo(DEFAULT_SMS_ENABLED);
        assertThat(testUserProfile.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
        assertThat(testUserProfile.getLastLoginIp()).isEqualTo(DEFAULT_LAST_LOGIN_IP);
        assertThat(testUserProfile.isIsLocked()).isEqualTo(DEFAULT_IS_LOCKED);
        assertThat(testUserProfile.getLockDate()).isEqualTo(DEFAULT_LOCK_DATE);
        assertThat(testUserProfile.getMaxImages()).isEqualTo(DEFAULT_MAX_IMAGES);
        assertThat(testUserProfile.getValidDays()).isEqualTo(DEFAULT_VALID_DAYS);
        assertThat(testUserProfile.isIsPrivacyEnabled()).isEqualTo(DEFAULT_IS_PRIVACY_ENABLED);
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].emailEnabled").value(hasItem(DEFAULT_EMAIL_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].smsEnabled").value(hasItem(DEFAULT_SMS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(DEFAULT_LAST_LOGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastLoginIp").value(hasItem(DEFAULT_LAST_LOGIN_IP)))
            .andExpect(jsonPath("$.[*].isLocked").value(hasItem(DEFAULT_IS_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].lockDate").value(hasItem(DEFAULT_LOCK_DATE.toString())))
            .andExpect(jsonPath("$.[*].maxImages").value(hasItem(DEFAULT_MAX_IMAGES)))
            .andExpect(jsonPath("$.[*].validDays").value(hasItem(DEFAULT_VALID_DAYS)))
            .andExpect(jsonPath("$.[*].isPrivacyEnabled").value(hasItem(DEFAULT_IS_PRIVACY_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.emailEnabled").value(DEFAULT_EMAIL_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.smsEnabled").value(DEFAULT_SMS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.lastLoginDate").value(DEFAULT_LAST_LOGIN_DATE.toString()))
            .andExpect(jsonPath("$.lastLoginIp").value(DEFAULT_LAST_LOGIN_IP))
            .andExpect(jsonPath("$.isLocked").value(DEFAULT_IS_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.lockDate").value(DEFAULT_LOCK_DATE.toString()))
            .andExpect(jsonPath("$.maxImages").value(DEFAULT_MAX_IMAGES))
            .andExpect(jsonPath("$.validDays").value(DEFAULT_VALID_DAYS))
            .andExpect(jsonPath("$.isPrivacyEnabled").value(DEFAULT_IS_PRIVACY_ENABLED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .phone(UPDATED_PHONE)
            .emailEnabled(UPDATED_EMAIL_ENABLED)
            .smsEnabled(UPDATED_SMS_ENABLED)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE)
            .lastLoginIp(UPDATED_LAST_LOGIN_IP)
            .isLocked(UPDATED_IS_LOCKED)
            .lockDate(UPDATED_LOCK_DATE)
            .maxImages(UPDATED_MAX_IMAGES)
            .validDays(UPDATED_VALID_DAYS)
            .isPrivacyEnabled(UPDATED_IS_PRIVACY_ENABLED);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserProfile.isEmailEnabled()).isEqualTo(UPDATED_EMAIL_ENABLED);
        assertThat(testUserProfile.isSmsEnabled()).isEqualTo(UPDATED_SMS_ENABLED);
        assertThat(testUserProfile.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
        assertThat(testUserProfile.getLastLoginIp()).isEqualTo(UPDATED_LAST_LOGIN_IP);
        assertThat(testUserProfile.isIsLocked()).isEqualTo(UPDATED_IS_LOCKED);
        assertThat(testUserProfile.getLockDate()).isEqualTo(UPDATED_LOCK_DATE);
        assertThat(testUserProfile.getMaxImages()).isEqualTo(UPDATED_MAX_IMAGES);
        assertThat(testUserProfile.getValidDays()).isEqualTo(UPDATED_VALID_DAYS);
        assertThat(testUserProfile.isIsPrivacyEnabled()).isEqualTo(UPDATED_IS_PRIVACY_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
