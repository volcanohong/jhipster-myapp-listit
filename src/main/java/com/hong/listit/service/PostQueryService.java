package com.hong.listit.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hong.listit.domain.Post;
import com.hong.listit.domain.*; // for static metamodels
import com.hong.listit.repository.PostRepository;
import com.hong.listit.service.dto.PostCriteria;
import com.hong.listit.service.dto.PostDTO;
import com.hong.listit.service.mapper.PostMapper;

/**
 * Service for executing complex queries for {@link Post} entities in the database.
 * The main input is a {@link PostCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostDTO} or a {@link Page} of {@link PostDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostQueryService extends QueryService<Post> {

    private final Logger log = LoggerFactory.getLogger(PostQueryService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostQueryService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Return a {@link List} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> findByCriteria(PostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postMapper.toDto(postRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findByCriteria(PostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.findAll(specification, page)
            .map(postMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.count(specification);
    }

    /**
     * Function to convert {@link PostCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Post> createSpecification(PostCriteria criteria) {
        Specification<Post> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Post_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Post_.name));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), Post_.detail));
            }
            if (criteria.getSearchText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSearchText(), Post_.searchText));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Post_.price));
            }
            if (criteria.getPriceNegotiable() != null) {
                specification = specification.and(buildSpecification(criteria.getPriceNegotiable(), Post_.priceNegotiable));
            }
            if (criteria.getCondition() != null) {
                specification = specification.and(buildSpecification(criteria.getCondition(), Post_.condition));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Post_.status));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Post_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Post_.lastModifiedDate));
            }
            if (criteria.getLastReviewedData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastReviewedData(), Post_.lastReviewedData));
            }
            if (criteria.getReviewedCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewedCount(), Post_.reviewedCount));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Post_.location, JoinType.LEFT).get(Location_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Post_.category, JoinType.LEFT).get(PostCategory_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Post_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getImageId() != null) {
                specification = specification.and(buildSpecification(criteria.getImageId(),
                    root -> root.join(Post_.image, JoinType.LEFT).get(Image_.id)));
            }
        }
        return specification;
    }
}
