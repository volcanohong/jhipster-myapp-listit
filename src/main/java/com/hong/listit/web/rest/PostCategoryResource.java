package com.hong.listit.web.rest;

import com.hong.listit.service.PostCategoryService;
import com.hong.listit.web.rest.errors.BadRequestAlertException;
import com.hong.listit.service.dto.PostCategoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hong.listit.domain.PostCategory}.
 */
@RestController
@RequestMapping("/api")
public class PostCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PostCategoryResource.class);

    private static final String ENTITY_NAME = "postCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostCategoryService postCategoryService;

    public PostCategoryResource(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    /**
     * {@code POST  /post-categories} : Create a new postCategory.
     *
     * @param postCategoryDTO the postCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postCategoryDTO, or with status {@code 400 (Bad Request)} if the postCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-categories")
    public ResponseEntity<PostCategoryDTO> createPostCategory(@Valid @RequestBody PostCategoryDTO postCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save PostCategory : {}", postCategoryDTO);
        if (postCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new postCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostCategoryDTO result = postCategoryService.save(postCategoryDTO);
        return ResponseEntity.created(new URI("/api/post-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-categories} : Updates an existing postCategory.
     *
     * @param postCategoryDTO the postCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the postCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-categories")
    public ResponseEntity<PostCategoryDTO> updatePostCategory(@Valid @RequestBody PostCategoryDTO postCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update PostCategory : {}", postCategoryDTO);
        if (postCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostCategoryDTO result = postCategoryService.save(postCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-categories} : get all the postCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postCategories in body.
     */
    @GetMapping("/post-categories")
    public List<PostCategoryDTO> getAllPostCategories() {
        log.debug("REST request to get all PostCategories");
        return postCategoryService.findAll();
    }

    /**
     * {@code GET  /post-categories/:id} : get the "id" postCategory.
     *
     * @param id the id of the postCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-categories/{id}")
    public ResponseEntity<PostCategoryDTO> getPostCategory(@PathVariable Long id) {
        log.debug("REST request to get PostCategory : {}", id);
        Optional<PostCategoryDTO> postCategoryDTO = postCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postCategoryDTO);
    }

    /**
     * {@code DELETE  /post-categories/:id} : delete the "id" postCategory.
     *
     * @param id the id of the postCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-categories/{id}")
    public ResponseEntity<Void> deletePostCategory(@PathVariable Long id) {
        log.debug("REST request to delete PostCategory : {}", id);
        postCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
