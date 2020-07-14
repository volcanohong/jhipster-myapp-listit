package com.hong.listit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hong.listit.domain.UserProfile} entity.
 */
public class UserProfileDTO implements Serializable {
    
    private Long id;

    @Size(max = 15)
    private String phone;

    private Boolean emailEnabled;

    private Boolean smsEnabled;

    private Instant lastLoginDate;

    private Integer lastLoginIp;

    private Boolean isLocked;

    private Instant lockDate;

    @Max(value = 20)
    private Integer maxImages;

    @Max(value = 90)
    private Integer validDays;

    private Boolean isPrivacyEnabled;


    private Long userId;

    private Long locationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isEmailEnabled() {
        return emailEnabled;
    }

    public void setEmailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public Boolean isSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Integer lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Instant getLockDate() {
        return lockDate;
    }

    public void setLockDate(Instant lockDate) {
        this.lockDate = lockDate;
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

    public Boolean isIsPrivacyEnabled() {
        return isPrivacyEnabled;
    }

    public void setIsPrivacyEnabled(Boolean isPrivacyEnabled) {
        this.isPrivacyEnabled = isPrivacyEnabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfileDTO)) {
            return false;
        }

        return id != null && id.equals(((UserProfileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfileDTO{" +
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
            ", userId=" + getUserId() +
            ", locationId=" + getLocationId() +
            "}";
    }
}
