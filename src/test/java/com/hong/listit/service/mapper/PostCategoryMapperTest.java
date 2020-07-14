package com.hong.listit.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostCategoryMapperTest {

    private PostCategoryMapper postCategoryMapper;

    @BeforeEach
    public void setUp() {
        postCategoryMapper = new PostCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(postCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postCategoryMapper.fromId(null)).isNull();
    }
}
