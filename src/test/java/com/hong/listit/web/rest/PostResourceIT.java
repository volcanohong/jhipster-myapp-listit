package com.hong.listit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hong.listit.ListitApp;
import com.hong.listit.domain.Image;
import com.hong.listit.domain.Location;
import com.hong.listit.domain.Post;
import com.hong.listit.domain.PostCategory;
import com.hong.listit.domain.User;
import com.hong.listit.domain.enumeration.PostStatus;
import com.hong.listit.domain.enumeration.ProductCondition;
import com.hong.listit.repository.PostRepository;
import com.hong.listit.service.PostQueryService;
import com.hong.listit.service.PostService;
import com.hong.listit.service.dto.PostDTO;
import com.hong.listit.service.mapper.PostMapper;
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
/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@SpringBootTest(classes = ListitApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_TEXT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Boolean DEFAULT_PRICE_NEGOTIABLE = false;
    private static final Boolean UPDATED_PRICE_NEGOTIABLE = true;

    private static final ProductCondition DEFAULT_CONDITION = ProductCondition.NEW;
    private static final ProductCondition UPDATED_CONDITION = ProductCondition.SUPERB;

    private static final PostStatus DEFAULT_STATUS = PostStatus.ACTIVE;
    private static final PostStatus UPDATED_STATUS = PostStatus.DELETED;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_REVIEWED_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_REVIEWED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_REVIEWED_COUNT = 1;
    private static final Integer UPDATED_REVIEWED_COUNT = 2;
    private static final Integer SMALLER_REVIEWED_COUNT = 1 - 1;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostQueryService postQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .searchText(DEFAULT_SEARCH_TEXT)
            .price(DEFAULT_PRICE)
            .priceNegotiable(DEFAULT_PRICE_NEGOTIABLE)
            .condition(DEFAULT_CONDITION)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastReviewedData(DEFAULT_LAST_REVIEWED_DATA)
            .reviewedCount(DEFAULT_REVIEWED_COUNT);
        return post;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .searchText(UPDATED_SEARCH_TEXT)
            .price(UPDATED_PRICE)
            .priceNegotiable(UPDATED_PRICE_NEGOTIABLE)
            .condition(UPDATED_CONDITION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastReviewedData(UPDATED_LAST_REVIEWED_DATA)
            .reviewedCount(UPDATED_REVIEWED_COUNT);
        return post;
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPost.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testPost.getSearchText()).isEqualTo(DEFAULT_SEARCH_TEXT);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPost.isPriceNegotiable()).isEqualTo(DEFAULT_PRICE_NEGOTIABLE);
        assertThat(testPost.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testPost.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPost.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPost.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPost.getLastReviewedData()).isEqualTo(DEFAULT_LAST_REVIEWED_DATA);
        assertThat(testPost.getReviewedCount()).isEqualTo(DEFAULT_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setName(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setStatus(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setCreatedDate(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].searchText").value(hasItem(DEFAULT_SEARCH_TEXT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceNegotiable").value(hasItem(DEFAULT_PRICE_NEGOTIABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastReviewedData").value(hasItem(DEFAULT_LAST_REVIEWED_DATA.toString())))
            .andExpect(jsonPath("$.[*].reviewedCount").value(hasItem(DEFAULT_REVIEWED_COUNT)));
    }

    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.searchText").value(DEFAULT_SEARCH_TEXT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.priceNegotiable").value(DEFAULT_PRICE_NEGOTIABLE.booleanValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastReviewedData").value(DEFAULT_LAST_REVIEWED_DATA.toString()))
            .andExpect(jsonPath("$.reviewedCount").value(DEFAULT_REVIEWED_COUNT));
    }


    @Test
    @Transactional
    public void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        Long id = post.getId();

        defaultPostShouldBeFound("id.equals=" + id);
        defaultPostShouldNotBeFound("id.notEquals=" + id);

        defaultPostShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.greaterThan=" + id);

        defaultPostShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name equals to DEFAULT_NAME
        defaultPostShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postList where name equals to UPDATED_NAME
        defaultPostShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name not equals to DEFAULT_NAME
        defaultPostShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the postList where name not equals to UPDATED_NAME
        defaultPostShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postList where name equals to UPDATED_NAME
        defaultPostShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name is not null
        defaultPostShouldBeFound("name.specified=true");

        // Get all the postList where name is null
        defaultPostShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByNameContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name contains DEFAULT_NAME
        defaultPostShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the postList where name contains UPDATED_NAME
        defaultPostShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where name does not contain DEFAULT_NAME
        defaultPostShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the postList where name does not contain UPDATED_NAME
        defaultPostShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPostsByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail equals to DEFAULT_DETAIL
        defaultPostShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the postList where detail equals to UPDATED_DETAIL
        defaultPostShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail not equals to DEFAULT_DETAIL
        defaultPostShouldNotBeFound("detail.notEquals=" + DEFAULT_DETAIL);

        // Get all the postList where detail not equals to UPDATED_DETAIL
        defaultPostShouldBeFound("detail.notEquals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultPostShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the postList where detail equals to UPDATED_DETAIL
        defaultPostShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail is not null
        defaultPostShouldBeFound("detail.specified=true");

        // Get all the postList where detail is null
        defaultPostShouldNotBeFound("detail.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByDetailContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail contains DEFAULT_DETAIL
        defaultPostShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the postList where detail contains UPDATED_DETAIL
        defaultPostShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllPostsByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where detail does not contain DEFAULT_DETAIL
        defaultPostShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the postList where detail does not contain UPDATED_DETAIL
        defaultPostShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }


    @Test
    @Transactional
    public void getAllPostsBySearchTextIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText equals to DEFAULT_SEARCH_TEXT
        defaultPostShouldBeFound("searchText.equals=" + DEFAULT_SEARCH_TEXT);

        // Get all the postList where searchText equals to UPDATED_SEARCH_TEXT
        defaultPostShouldNotBeFound("searchText.equals=" + UPDATED_SEARCH_TEXT);
    }

    @Test
    @Transactional
    public void getAllPostsBySearchTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText not equals to DEFAULT_SEARCH_TEXT
        defaultPostShouldNotBeFound("searchText.notEquals=" + DEFAULT_SEARCH_TEXT);

        // Get all the postList where searchText not equals to UPDATED_SEARCH_TEXT
        defaultPostShouldBeFound("searchText.notEquals=" + UPDATED_SEARCH_TEXT);
    }

    @Test
    @Transactional
    public void getAllPostsBySearchTextIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText in DEFAULT_SEARCH_TEXT or UPDATED_SEARCH_TEXT
        defaultPostShouldBeFound("searchText.in=" + DEFAULT_SEARCH_TEXT + "," + UPDATED_SEARCH_TEXT);

        // Get all the postList where searchText equals to UPDATED_SEARCH_TEXT
        defaultPostShouldNotBeFound("searchText.in=" + UPDATED_SEARCH_TEXT);
    }

    @Test
    @Transactional
    public void getAllPostsBySearchTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText is not null
        defaultPostShouldBeFound("searchText.specified=true");

        // Get all the postList where searchText is null
        defaultPostShouldNotBeFound("searchText.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsBySearchTextContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText contains DEFAULT_SEARCH_TEXT
        defaultPostShouldBeFound("searchText.contains=" + DEFAULT_SEARCH_TEXT);

        // Get all the postList where searchText contains UPDATED_SEARCH_TEXT
        defaultPostShouldNotBeFound("searchText.contains=" + UPDATED_SEARCH_TEXT);
    }

    @Test
    @Transactional
    public void getAllPostsBySearchTextNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where searchText does not contain DEFAULT_SEARCH_TEXT
        defaultPostShouldNotBeFound("searchText.doesNotContain=" + DEFAULT_SEARCH_TEXT);

        // Get all the postList where searchText does not contain UPDATED_SEARCH_TEXT
        defaultPostShouldBeFound("searchText.doesNotContain=" + UPDATED_SEARCH_TEXT);
    }


    @Test
    @Transactional
    public void getAllPostsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price equals to DEFAULT_PRICE
        defaultPostShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the postList where price equals to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price not equals to DEFAULT_PRICE
        defaultPostShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the postList where price not equals to UPDATED_PRICE
        defaultPostShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPostShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the postList where price equals to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is not null
        defaultPostShouldBeFound("price.specified=true");

        // Get all the postList where price is null
        defaultPostShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is greater than or equal to DEFAULT_PRICE
        defaultPostShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the postList where price is greater than or equal to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is less than or equal to DEFAULT_PRICE
        defaultPostShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the postList where price is less than or equal to SMALLER_PRICE
        defaultPostShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is less than DEFAULT_PRICE
        defaultPostShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the postList where price is less than UPDATED_PRICE
        defaultPostShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is greater than DEFAULT_PRICE
        defaultPostShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the postList where price is greater than SMALLER_PRICE
        defaultPostShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllPostsByPriceNegotiableIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where priceNegotiable equals to DEFAULT_PRICE_NEGOTIABLE
        defaultPostShouldBeFound("priceNegotiable.equals=" + DEFAULT_PRICE_NEGOTIABLE);

        // Get all the postList where priceNegotiable equals to UPDATED_PRICE_NEGOTIABLE
        defaultPostShouldNotBeFound("priceNegotiable.equals=" + UPDATED_PRICE_NEGOTIABLE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceNegotiableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where priceNegotiable not equals to DEFAULT_PRICE_NEGOTIABLE
        defaultPostShouldNotBeFound("priceNegotiable.notEquals=" + DEFAULT_PRICE_NEGOTIABLE);

        // Get all the postList where priceNegotiable not equals to UPDATED_PRICE_NEGOTIABLE
        defaultPostShouldBeFound("priceNegotiable.notEquals=" + UPDATED_PRICE_NEGOTIABLE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceNegotiableIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where priceNegotiable in DEFAULT_PRICE_NEGOTIABLE or UPDATED_PRICE_NEGOTIABLE
        defaultPostShouldBeFound("priceNegotiable.in=" + DEFAULT_PRICE_NEGOTIABLE + "," + UPDATED_PRICE_NEGOTIABLE);

        // Get all the postList where priceNegotiable equals to UPDATED_PRICE_NEGOTIABLE
        defaultPostShouldNotBeFound("priceNegotiable.in=" + UPDATED_PRICE_NEGOTIABLE);
    }

    @Test
    @Transactional
    public void getAllPostsByPriceNegotiableIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where priceNegotiable is not null
        defaultPostShouldBeFound("priceNegotiable.specified=true");

        // Get all the postList where priceNegotiable is null
        defaultPostShouldNotBeFound("priceNegotiable.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where condition equals to DEFAULT_CONDITION
        defaultPostShouldBeFound("condition.equals=" + DEFAULT_CONDITION);

        // Get all the postList where condition equals to UPDATED_CONDITION
        defaultPostShouldNotBeFound("condition.equals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllPostsByConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where condition not equals to DEFAULT_CONDITION
        defaultPostShouldNotBeFound("condition.notEquals=" + DEFAULT_CONDITION);

        // Get all the postList where condition not equals to UPDATED_CONDITION
        defaultPostShouldBeFound("condition.notEquals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllPostsByConditionIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where condition in DEFAULT_CONDITION or UPDATED_CONDITION
        defaultPostShouldBeFound("condition.in=" + DEFAULT_CONDITION + "," + UPDATED_CONDITION);

        // Get all the postList where condition equals to UPDATED_CONDITION
        defaultPostShouldNotBeFound("condition.in=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllPostsByConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where condition is not null
        defaultPostShouldBeFound("condition.specified=true");

        // Get all the postList where condition is null
        defaultPostShouldNotBeFound("condition.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status equals to DEFAULT_STATUS
        defaultPostShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the postList where status equals to UPDATED_STATUS
        defaultPostShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPostsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status not equals to DEFAULT_STATUS
        defaultPostShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the postList where status not equals to UPDATED_STATUS
        defaultPostShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPostsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPostShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the postList where status equals to UPDATED_STATUS
        defaultPostShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPostsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status is not null
        defaultPostShouldBeFound("status.specified=true");

        // Get all the postList where status is null
        defaultPostShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPostShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the postList where createdDate equals to UPDATED_CREATED_DATE
        defaultPostShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultPostShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the postList where createdDate not equals to UPDATED_CREATED_DATE
        defaultPostShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPostShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the postList where createdDate equals to UPDATED_CREATED_DATE
        defaultPostShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where createdDate is not null
        defaultPostShouldBeFound("createdDate.specified=true");

        // Get all the postList where createdDate is null
        defaultPostShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPostShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the postList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPostShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPostShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the postList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultPostShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultPostShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the postList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPostShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastModifiedDate is not null
        defaultPostShouldBeFound("lastModifiedDate.specified=true");

        // Get all the postList where lastModifiedDate is null
        defaultPostShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByLastReviewedDataIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastReviewedData equals to DEFAULT_LAST_REVIEWED_DATA
        defaultPostShouldBeFound("lastReviewedData.equals=" + DEFAULT_LAST_REVIEWED_DATA);

        // Get all the postList where lastReviewedData equals to UPDATED_LAST_REVIEWED_DATA
        defaultPostShouldNotBeFound("lastReviewedData.equals=" + UPDATED_LAST_REVIEWED_DATA);
    }

    @Test
    @Transactional
    public void getAllPostsByLastReviewedDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastReviewedData not equals to DEFAULT_LAST_REVIEWED_DATA
        defaultPostShouldNotBeFound("lastReviewedData.notEquals=" + DEFAULT_LAST_REVIEWED_DATA);

        // Get all the postList where lastReviewedData not equals to UPDATED_LAST_REVIEWED_DATA
        defaultPostShouldBeFound("lastReviewedData.notEquals=" + UPDATED_LAST_REVIEWED_DATA);
    }

    @Test
    @Transactional
    public void getAllPostsByLastReviewedDataIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastReviewedData in DEFAULT_LAST_REVIEWED_DATA or UPDATED_LAST_REVIEWED_DATA
        defaultPostShouldBeFound("lastReviewedData.in=" + DEFAULT_LAST_REVIEWED_DATA + "," + UPDATED_LAST_REVIEWED_DATA);

        // Get all the postList where lastReviewedData equals to UPDATED_LAST_REVIEWED_DATA
        defaultPostShouldNotBeFound("lastReviewedData.in=" + UPDATED_LAST_REVIEWED_DATA);
    }

    @Test
    @Transactional
    public void getAllPostsByLastReviewedDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where lastReviewedData is not null
        defaultPostShouldBeFound("lastReviewedData.specified=true");

        // Get all the postList where lastReviewedData is null
        defaultPostShouldNotBeFound("lastReviewedData.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount equals to DEFAULT_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.equals=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount equals to UPDATED_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.equals=" + UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount not equals to DEFAULT_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.notEquals=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount not equals to UPDATED_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.notEquals=" + UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount in DEFAULT_REVIEWED_COUNT or UPDATED_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.in=" + DEFAULT_REVIEWED_COUNT + "," + UPDATED_REVIEWED_COUNT);

        // Get all the postList where reviewedCount equals to UPDATED_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.in=" + UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount is not null
        defaultPostShouldBeFound("reviewedCount.specified=true");

        // Get all the postList where reviewedCount is null
        defaultPostShouldNotBeFound("reviewedCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount is greater than or equal to DEFAULT_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.greaterThanOrEqual=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount is greater than or equal to UPDATED_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.greaterThanOrEqual=" + UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount is less than or equal to DEFAULT_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.lessThanOrEqual=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount is less than or equal to SMALLER_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.lessThanOrEqual=" + SMALLER_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount is less than DEFAULT_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.lessThan=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount is less than UPDATED_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.lessThan=" + UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void getAllPostsByReviewedCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where reviewedCount is greater than DEFAULT_REVIEWED_COUNT
        defaultPostShouldNotBeFound("reviewedCount.greaterThan=" + DEFAULT_REVIEWED_COUNT);

        // Get all the postList where reviewedCount is greater than SMALLER_REVIEWED_COUNT
        defaultPostShouldBeFound("reviewedCount.greaterThan=" + SMALLER_REVIEWED_COUNT);
    }


    @Test
    @Transactional
    public void getAllPostsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        post.setLocation(location);
        postRepository.saveAndFlush(post);
        Long locationId = location.getId();

        // Get all the postList where location equals to locationId
        defaultPostShouldBeFound("locationId.equals=" + locationId);

        // Get all the postList where location equals to locationId + 1
        defaultPostShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        PostCategory category = PostCategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        post.setCategory(category);
        postRepository.saveAndFlush(post);
        Long categoryId = category.getId();

        // Get all the postList where category equals to categoryId
        defaultPostShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the postList where category equals to categoryId + 1
        defaultPostShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        post.setUser(user);
        postRepository.saveAndFlush(post);
        Long userId = user.getId();

        // Get all the postList where user equals to userId
        defaultPostShouldBeFound("userId.equals=" + userId);

        // Get all the postList where user equals to userId + 1
        defaultPostShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Image image = ImageResourceIT.createEntity(em);
        em.persist(image);
        em.flush();
        post.addImage(image);
        postRepository.saveAndFlush(post);
        Long imageId = image.getId();

        // Get all the postList where image equals to imageId
        defaultPostShouldBeFound("imageId.equals=" + imageId);

        // Get all the postList where image equals to imageId + 1
        defaultPostShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostShouldBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].searchText").value(hasItem(DEFAULT_SEARCH_TEXT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceNegotiable").value(hasItem(DEFAULT_PRICE_NEGOTIABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastReviewedData").value(hasItem(DEFAULT_LAST_REVIEWED_DATA.toString())))
            .andExpect(jsonPath("$.[*].reviewedCount").value(hasItem(DEFAULT_REVIEWED_COUNT)));

        // Check, that the count call also returns 1
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostShouldNotBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .searchText(UPDATED_SEARCH_TEXT)
            .price(UPDATED_PRICE)
            .priceNegotiable(UPDATED_PRICE_NEGOTIABLE)
            .condition(UPDATED_CONDITION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastReviewedData(UPDATED_LAST_REVIEWED_DATA)
            .reviewedCount(UPDATED_REVIEWED_COUNT);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPost.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testPost.getSearchText()).isEqualTo(UPDATED_SEARCH_TEXT);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.isPriceNegotiable()).isEqualTo(UPDATED_PRICE_NEGOTIABLE);
        assertThat(testPost.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testPost.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPost.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPost.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPost.getLastReviewedData()).isEqualTo(UPDATED_LAST_REVIEWED_DATA);
        assertThat(testPost.getReviewedCount()).isEqualTo(UPDATED_REVIEWED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Delete the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
