package com.hong.listit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hong.listit.domain.enumeration.PostStatus;
import com.hong.listit.domain.enumeration.ProductCondition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 1024)
    @Column(name = "detail", length = 1024)
    private String detail;

    @Size(max = 50)
    @Column(name = "search_text", length = 50)
    private String searchText;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_negotiable")
    private Boolean priceNegotiable;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_condition")
    private ProductCondition condition;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_reviewed_data")
    private Instant lastReviewedData;

    @Column(name = "reviewed_count")
    private Integer reviewedCount;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToOne
    @JoinColumn(unique = true)
    private PostCategory category;

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "post")
    private Set<Image> images = new HashSet<>();

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

    public Post name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public Post detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSearchText() {
        return searchText;
    }

    public Post searchText(String searchText) {
        this.searchText = searchText;
        return this;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Double getPrice() {
        return price;
    }

    public Post price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isPriceNegotiable() {
        return priceNegotiable;
    }

    public Post priceNegotiable(Boolean priceNegotiable) {
        this.priceNegotiable = priceNegotiable;
        return this;
    }

    public void setPriceNegotiable(Boolean priceNegotiable) {
        this.priceNegotiable = priceNegotiable;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public Post condition(ProductCondition condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

    public PostStatus getStatus() {
        return status;
    }

    public Post status(PostStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Post createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Post lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getLastReviewedData() {
        return lastReviewedData;
    }

    public Post lastReviewedData(Instant lastReviewedData) {
        this.lastReviewedData = lastReviewedData;
        return this;
    }

    public void setLastReviewedData(Instant lastReviewedData) {
        this.lastReviewedData = lastReviewedData;
    }

    public Integer getReviewedCount() {
        return reviewedCount;
    }

    public Post reviewedCount(Integer reviewedCount) {
        this.reviewedCount = reviewedCount;
        return this;
    }

    public void setReviewedCount(Integer reviewedCount) {
        this.reviewedCount = reviewedCount;
    }

    public Location getLocation() {
        return location;
    }

    public Post location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PostCategory getCategory() {
        return category;
    }

    public Post category(PostCategory postCategory) {
        this.category = postCategory;
        return this;
    }

    public void setCategory(PostCategory postCategory) {
        this.category = postCategory;
    }

    public User getUser() {
        return user;
    }

    public Post user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Post images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Post addImage(Image image) {
        this.images.add(image);
        image.setPost(this);
        return this;
    }

    public Post removeImage(Image image) {
        this.images.remove(image);
        image.setPost(null);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
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
            "}";
    }
}
