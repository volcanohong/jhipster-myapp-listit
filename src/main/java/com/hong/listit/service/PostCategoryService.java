package com.hong.listit.service;

import com.hong.listit.domain.PostCategory;
import com.hong.listit.repository.PostCategoryRepository;
import com.hong.listit.service.dto.PostCategoryDTO;
import com.hong.listit.service.mapper.PostCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PostCategory}.
 */
@Service
@Transactional
public class PostCategoryService {

    private final Logger log = LoggerFactory.getLogger(PostCategoryService.class);

    private final PostCategoryRepository postCategoryRepository;

    private final PostCategoryMapper postCategoryMapper;

    public PostCategoryService(PostCategoryRepository postCategoryRepository, PostCategoryMapper postCategoryMapper) {
        this.postCategoryRepository = postCategoryRepository;
        this.postCategoryMapper = postCategoryMapper;
    }

    /**
     * Save a postCategory.
     *
     * @param postCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public PostCategoryDTO save(PostCategoryDTO postCategoryDTO) {
        log.debug("Request to save PostCategory : {}", postCategoryDTO);
        PostCategory postCategory = postCategoryMapper.toEntity(postCategoryDTO);
        postCategory = postCategoryRepository.save(postCategory);
        return postCategoryMapper.toDto(postCategory);
    }

    /**
     * Get all the postCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostCategoryDTO> findAll() {
        log.debug("Request to get all PostCategories");
        return postCategoryRepository.findAll().stream()
            .map(postCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one postCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostCategoryDTO> findOne(Long id) {
        log.debug("Request to get PostCategory : {}", id);
        return postCategoryRepository.findById(id)
            .map(postCategoryMapper::toDto);
    }

    /**
     * Delete the postCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostCategory : {}", id);
        postCategoryRepository.deleteById(id);
    }
}
