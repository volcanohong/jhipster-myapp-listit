package com.hong.listit.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email_enabled")
    private Boolean emailEnabled;

    @Column(name = "sms_enabled")
    private Boolean smsEnabled;

    @Column(name = "last_login_date")
    private Instant lastLoginDate;

    @Column(name = "last_login_ip")
    private Integer lastLoginIp;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "lock_date")
    private Instant lockDate;

    @Max(value = 20)
    @Column(name = "max_images")
    private Integer maxImages;

    @Max(value = 90)
    @Column(name = "valid_days")
    private Integer validDays;

    @Column(name = "is_privacy_enabled")
    private Boolean isPrivacyEnabled;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public UserProfile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isEmailEnabled() {
        return emailEnabled;
    }

    public UserProfile emailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
        return this;
    }

    public void setEmailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public Boolean isSmsEnabled() {
        return smsEnabled;
    }

    public UserProfile smsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
        return this;
    }

    public void setSmsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public UserProfile lastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLastLoginIp() {
        return lastLoginIp;
    }

    public UserProfile lastLoginIp(Integer lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public void setLastLoginIp(Integer lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Boolean isIsLocked() {
        return isLocked;
    }

    public UserProfile isLocked(Boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Instant getLockDate() {
        return lockDate;
    }

    public UserProfile lockDate(Instant lockDate) {
        this.lockDate = lockDate;
        return this;
    }

    public void setLockDate(Instant lockDate) {
        this.lockDate = lockDate;
    }

    public Integer getMaxImages() {
        return maxImages;
    }

    public UserProfile maxImages(Integer maxImages) {
        this.maxImages = maxImages;
        return this;
    }

    public void setMaxImages(Integer maxImages) {
        this.maxImages = maxImages;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public UserProfile validDays(Integer validDays) {
        this.validDays = validDays;
        return this;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Boolean isIsPrivacyEnabled() {
        return isPrivacyEnabled;
    }

    public UserProfile isPrivacyEnabled(Boolean isPrivacyEnabled) {
        this.isPrivacyEnabled = isPrivacyEnabled;
        return this;
    }

    public void setIsPrivacyEnabled(Boolean isPrivacyEnabled) {
        this.isPrivacyEnabled = isPrivacyEnabled;
    }

    public User getUser() {
        return user;
    }

    public UserProfile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public UserProfile location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", emailEnabled='" + isEmailEnabled() + "'" +
            ", smsEnabled='" + isSmsEnabled() + "'" +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            ", lastLoginIp=" + getLastLoginIp() +
            ", isLocked='" + isIsLocked() + "'" +
            ", lockDate='" + getLockDate() + "'" +
            ", maxImages=" + getMaxImages() +
            ", validDays=" + getValidDays() +
            ", isPrivacyEnabled='" + isIsPrivacyEnabled() + "'" +
            "}";
    }
}
