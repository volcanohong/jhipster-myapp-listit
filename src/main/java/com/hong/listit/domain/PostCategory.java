package com.hong.listit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PostCategory.
 */
@Entity
@Table(name = "post_category")
public class PostCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Max(value = 20)
    @Column(name = "max_images")
    private Integer maxImages;

    @Max(value = 90)
    @Column(name = "valid_days")
    private Integer validDays;

    @ManyToOne
    @JsonIgnoreProperties(value = "postCategories", allowSetters = true)
    private PostCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PostCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public PostCategory isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getMaxImages() {
        return maxImages;
    }

    public PostCategory maxImages(Integer maxImages) {
        this.maxImages = maxImages;
        return this;
    }

    public void setMaxImages(Integer maxImages) {
        this.maxImages = maxImages;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public PostCategory validDays(Integer validDays) {
        this.validDays = validDays;
        return this;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public PostCategory getCategory() {
        return category;
    }

    public PostCategory category(PostCategory postCategory) {
        this.category = postCategory;
        return this;
    }

    public void setCategory(PostCategory postCategory) {
        this.category = postCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostCategory)) {
            return false;
        }
        return id != null && id.equals(((PostCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", maxImages=" + getMaxImages() +
            ", validDays=" + getValidDays() +
            "}";
    }
}
