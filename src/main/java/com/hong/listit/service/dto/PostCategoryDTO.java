package com.hong.listit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hong.listit.domain.PostCategory} entity.
 */
public class PostCategoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private Boolean isEnabled;

    @Max(value = 20)
    private Integer maxImages;

    @Max(value = 90)
    private Integer validDays;


    private Long categoryId;
    
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

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getMaxImages() {
        return maxImages;
    }

    public void setMaxImages(Integer maxImages) {
        this.maxImages = maxImages;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long postCategoryId) {
        this.categoryId = postCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostCategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((PostCategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", maxImages=" + getMaxImages() +
            ", validDays=" + getValidDays() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
