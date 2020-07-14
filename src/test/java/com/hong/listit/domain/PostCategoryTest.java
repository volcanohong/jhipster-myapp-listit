package com.hong.listit.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hong.listit.web.rest.TestUtil;

public class PostCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCategory.class);
        PostCategory postCategory1 = new PostCategory();
        postCategory1.setId(1L);
        PostCategory postCategory2 = new PostCategory();
        postCategory2.setId(postCategory1.getId());
        assertThat(postCategory1).isEqualTo(postCategory2);
        postCategory2.setId(2L);
        assertThat(postCategory1).isNotEqualTo(postCategory2);
        postCategory1.setId(null);
        assertThat(postCategory1).isNotEqualTo(postCategory2);
    }
}
