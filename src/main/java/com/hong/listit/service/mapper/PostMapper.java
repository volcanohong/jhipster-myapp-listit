package com.hong.listit.service.mapper;


import com.hong.listit.domain.*;
import com.hong.listit.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, PostCategoryMapper.class, UserMapper.class, ImageMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "image.id", target = "imageId")
    PostDTO toDto(Post post);

    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "imageId", target = "image")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
