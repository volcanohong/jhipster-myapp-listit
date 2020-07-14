package com.hong.listit.web.rest;

import com.hong.listit.ListitApp;
import com.hong.listit.domain.PostCategory;
import com.hong.listit.repository.PostCategoryRepository;
import com.hong.listit.service.PostCategoryService;
import com.hong.listit.service.dto.PostCategoryDTO;
import com.hong.listit.service.mapper.PostCategoryMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PostCategoryResource} REST controller.
 */
@SpringBootTest(classes = ListitApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final Integer DEFAULT_MAX_IMAGES = 20;
    private static final Integer UPDATED_MAX_IMAGES = 19;

    private static final Integer DEFAULT_VALID_DAYS = 90;
    private static final Integer UPDATED_VALID_DAYS = 89;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private PostCategoryMapper postCategoryMapper;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostCategoryMockMvc;

    private PostCategory postCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCategory createEntity(EntityManager em) {
        PostCategory postCategory = new PostCategory()
            .name(DEFAULT_NAME)
            .isEnabled(DEFAULT_IS_ENABLED)
            .maxImages(DEFAULT_MAX_IMAGES)
            .validDays(DEFAULT_VALID_DAYS);
        return postCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCategory createUpdatedEntity(EntityManager em) {
        PostCategory postCategory = new PostCategory()
            .name(UPDATED_NAME)
            .isEnabled(UPDATED_IS_ENABLED)
            .maxImages(UPDATED_MAX_IMAGES)
            .validDays(UPDATED_VALID_DAYS);
        return postCategory;
    }

    @BeforeEach
    public void initTest() {
        postCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostCategory() throws Exception {
        int databaseSizeBeforeCreate = postCategoryRepository.findAll().size();
        // Create the PostCategory
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);
        restPostCategoryMockMvc.perform(post("/api/post-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PostCategory testPostCategory = postCategoryList.get(postCategoryList.size() - 1);
        assertThat(testPostCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPostCategory.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testPostCategory.getMaxImages()).isEqualTo(DEFAULT_MAX_IMAGES);
        assertThat(testPostCategory.getValidDays()).isEqualTo(DEFAULT_VALID_DAYS);
    }

    @Test
    @Transactional
    public void createPostCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postCategoryRepository.findAll().size();

        // Create the PostCategory with an existing ID
        postCategory.setId(1L);
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostCategoryMockMvc.perform(post("/api/post-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postCategoryRepository.findAll().size();
        // set the field null
        postCategory.setName(null);

        // Create the PostCategory, which fails.
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);


        restPostCategoryMockMvc.perform(post("/api/post-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostCategories() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get all the postCategoryList
        restPostCategoryMockMvc.perform(get("/api/post-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].maxImages").value(hasItem(DEFAULT_MAX_IMAGES)))
            .andExpect(jsonPath("$.[*].validDays").value(hasItem(DEFAULT_VALID_DAYS)));
    }
    
    @Test
    @Transactional
    public void getPostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get the postCategory
        restPostCategoryMockMvc.perform(get("/api/post-categories/{id}", postCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.maxImages").value(DEFAULT_MAX_IMAGES))
            .andExpect(jsonPath("$.validDays").value(DEFAULT_VALID_DAYS));
    }
    @Test
    @Transactional
    public void getNonExistingPostCategory() throws Exception {
        // Get the postCategory
        restPostCategoryMockMvc.perform(get("/api/post-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        int databaseSizeBeforeUpdate = postCategoryRepository.findAll().size();

        // Update the postCategory
        PostCategory updatedPostCategory = postCategoryRepository.findById(postCategory.getId()).get();
        // Disconnect from session so that the updates on updatedPostCategory are not directly saved in db
        em.detach(updatedPostCategory);
        updatedPostCategory
            .name(UPDATED_NAME)
            .isEnabled(UPDATED_IS_ENABLED)
            .maxImages(UPDATED_MAX_IMAGES)
            .validDays(UPDATED_VALID_DAYS);
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(updatedPostCategory);

        restPostCategoryMockMvc.perform(put("/api/post-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeUpdate);
        PostCategory testPostCategory = postCategoryList.get(postCategoryList.size() - 1);
        assertThat(testPostCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostCategory.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testPostCategory.getMaxImages()).isEqualTo(UPDATED_MAX_IMAGES);
        assertThat(testPostCategory.getValidDays()).isEqualTo(UPDATED_VALID_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingPostCategory() throws Exception {
        int databaseSizeBeforeUpdate = postCategoryRepository.findAll().size();

        // Create the PostCategory
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostCategoryMockMvc.perform(put("/api/post-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        int databaseSizeBeforeDelete = postCategoryRepository.findAll().size();

        // Delete the postCategory
        restPostCategoryMockMvc.perform(delete("/api/post-categories/{id}", postCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
