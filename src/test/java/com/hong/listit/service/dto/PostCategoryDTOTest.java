package com.hong.listit.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hong.listit.web.rest.TestUtil;

public class PostCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCategoryDTO.class);
        PostCategoryDTO postCategoryDTO1 = new PostCategoryDTO();
        postCategoryDTO1.setId(1L);
        PostCategoryDTO postCategoryDTO2 = new PostCategoryDTO();
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
        postCategoryDTO2.setId(postCategoryDTO1.getId());
        assertThat(postCategoryDTO1).isEqualTo(postCategoryDTO2);
        postCategoryDTO2.setId(2L);
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
        postCategoryDTO1.setId(null);
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
    }
}
