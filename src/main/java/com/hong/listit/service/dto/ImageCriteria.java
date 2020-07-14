package com.hong.listit.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.hong.listit.domain.Image} entity. This class is used
 * in {@link com.hong.listit.web.rest.ImageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter url;

    private BooleanFilter isTop;

    private LongFilter postId;

    public ImageCriteria() {
    }

    public ImageCriteria(ImageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.isTop = other.isTop == null ? null : other.isTop.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public ImageCriteria copy() {
        return new ImageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public BooleanFilter getIsTop() {
        return isTop;
    }

    public void setIsTop(BooleanFilter isTop) {
        this.isTop = isTop;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageCriteria that = (ImageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(url, that.url) &&
            Objects.equals(isTop, that.isTop) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        url,
        isTop,
        postId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (isTop != null ? "isTop=" + isTop + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
