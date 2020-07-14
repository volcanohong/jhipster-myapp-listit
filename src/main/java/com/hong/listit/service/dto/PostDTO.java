package com.hong.listit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.hong.listit.domain.enumeration.ProductCondition;
import com.hong.listit.domain.enumeration.PostStatus;

/**
 * A DTO for the {@link com.hong.listit.domain.Post} entity.
 */
public class PostDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Size(max = 1024)
    private String detail;

    @Size(max = 50)
    private String searchText;

    private Double price;

    private Boolean priceNegotiable;

    private ProductCondition condition;

    @NotNull
    private PostStatus status;

    @NotNull
    private Instant createdDate;

    private Instant lastModifiedDate;

    private Instant lastReviewedData;

    private Integer reviewedCount;


    private Long locationId;

    private Long categoryId;

    private Long userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isPriceNegotiable() {
        return priceNegotiable;
    }

    public void setPriceNegotiable(Boolean priceNegotiable) {
        this.priceNegotiable = priceNegotiable;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getLastReviewedData() {
        return lastReviewedData;
    }

    public void setLastReviewedData(Instant lastReviewedData) {
        this.lastReviewedData = lastReviewedData;
    }

    public Integer getReviewedCount() {
        return reviewedCount;
    }

    public void setReviewedCount(Integer reviewedCount) {
        this.reviewedCount = reviewedCount;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long postCategoryId) {
        this.categoryId = postCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", searchText='" + getSearchText() + "'" +
            ", price=" + getPrice() +
            ", priceNegotiable='" + isPriceNegotiable() + "'" +
            ", condition='" + getCondition() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastReviewedData='" + getLastReviewedData() + "'" +
            ", reviewedCount=" + getReviewedCount() +
            ", locationId=" + getLocationId() +
            ", categoryId=" + getCategoryId() +
            ", userId=" + getUserId() +
            "}";
    }
}
