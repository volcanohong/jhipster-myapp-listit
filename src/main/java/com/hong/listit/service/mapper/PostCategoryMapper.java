package com.hong.listit.service.mapper;


import com.hong.listit.domain.*;
import com.hong.listit.service.dto.PostCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostCategory} and its DTO {@link PostCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostCategoryMapper extends EntityMapper<PostCategoryDTO, PostCategory> {

    @Mapping(source = "category.id", target = "categoryId")
    PostCategoryDTO toDto(PostCategory postCategory);

    @Mapping(source = "categoryId", target = "category")
    PostCategory toEntity(PostCategoryDTO postCategoryDTO);

    default PostCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostCategory postCategory = new PostCategory();
        postCategory.setId(id);
        return postCategory;
    }
}
