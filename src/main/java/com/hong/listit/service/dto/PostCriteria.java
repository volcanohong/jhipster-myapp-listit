package com.hong.listit.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.hong.listit.domain.enumeration.ProductCondition;
import com.hong.listit.domain.enumeration.PostStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.hong.listit.domain.Post} entity. This class is used
 * in {@link com.hong.listit.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProductCondition
     */
    public static class ProductConditionFilter extends Filter<ProductCondition> {

        public ProductConditionFilter() {
        }

        public ProductConditionFilter(ProductConditionFilter filter) {
            super(filter);
        }

        @Override
        public ProductConditionFilter copy() {
            return new ProductConditionFilter(this);
        }

    }
    /**
     * Class for filtering PostStatus
     */
    public static class PostStatusFilter extends Filter<PostStatus> {

        public PostStatusFilter() {
        }

        public PostStatusFilter(PostStatusFilter filter) {
            super(filter);
        }

        @Override
        public PostStatusFilter copy() {
            return new PostStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter detail;

    private StringFilter searchText;

    private DoubleFilter price;

    private BooleanFilter priceNegotiable;

    private ProductConditionFilter condition;

    private PostStatusFilter status;

    private InstantFilter createdDate;

    private InstantFilter lastModifiedDate;

    private InstantFilter lastReviewedData;

    private IntegerFilter reviewedCount;

    private LongFilter locationId;

    private LongFilter categoryId;

    private LongFilter userId;

    private LongFilter imageId;

    public PostCriteria() {
    }

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.searchText = other.searchText == null ? null : other.searchText.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.priceNegotiable = other.priceNegotiable == null ? null : other.priceNegotiable.copy();
        this.condition = other.condition == null ? null : other.condition.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.lastReviewedData = other.lastReviewedData == null ? null : other.lastReviewedData.copy();
        this.reviewedCount = other.reviewedCount == null ? null : other.reviewedCount.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDetail() {
        return detail;
    }

    public void setDetail(StringFilter detail) {
        this.detail = detail;
    }

    public StringFilter getSearchText() {
        return searchText;
    }

    public void setSearchText(StringFilter searchText) {
        this.searchText = searchText;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public BooleanFilter getPriceNegotiable() {
        return priceNegotiable;
    }

    public void setPriceNegotiable(BooleanFilter priceNegotiable) {
        this.priceNegotiable = priceNegotiable;
    }

    public ProductConditionFilter getCondition() {
        return condition;
    }

    public void setCondition(ProductConditionFilter condition) {
        this.condition = condition;
    }

    public PostStatusFilter getStatus() {
        return status;
    }

    public void setStatus(PostStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public InstantFilter getLastReviewedData() {
        return lastReviewedData;
    }

    public void setLastReviewedData(InstantFilter lastReviewedData) {
        this.lastReviewedData = lastReviewedData;
    }

    public IntegerFilter getReviewedCount() {
        return reviewedCount;
    }

    public void setReviewedCount(IntegerFilter reviewedCount) {
        this.reviewedCount = reviewedCount;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getImageId() {
        return imageId;
    }

    public void setImageId(LongFilter imageId) {
        this.imageId = imageId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostCriteria that = (PostCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(searchText, that.searchText) &&
            Objects.equals(price, that.price) &&
            Objects.equals(priceNegotiable, that.priceNegotiable) &&
            Objects.equals(condition, that.condition) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastReviewedData, that.lastReviewedData) &&
            Objects.equals(reviewedCount, that.reviewedCount) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        detail,
        searchText,
        price,
        priceNegotiable,
        condition,
        status,
        createdDate,
        lastModifiedDate,
        lastReviewedData,
        reviewedCount,
        locationId,
        categoryId,
        userId,
        imageId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (detail != null ? "detail=" + detail + ", " : "") +
                (searchText != null ? "searchText=" + searchText + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (priceNegotiable != null ? "priceNegotiable=" + priceNegotiable + ", " : "") +
                (condition != null ? "condition=" + condition + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (lastReviewedData != null ? "lastReviewedData=" + lastReviewedData + ", " : "") +
                (reviewedCount != null ? "reviewedCount=" + reviewedCount + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (imageId != null ? "imageId=" + imageId + ", " : "") +
            "}";
    }

}
