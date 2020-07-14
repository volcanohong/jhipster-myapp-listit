package com.hong.listit.repository;

import com.hong.listit.domain.PostCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PostCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
