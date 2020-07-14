package com.hong.listit.service.mapper;


import com.hong.listit.domain.*;
import com.hong.listit.service.dto.UserProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProfile} and its DTO {@link UserProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LocationMapper.class})
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "location.id", target = "locationId")
    UserProfileDTO toDto(UserProfile userProfile);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "locationId", target = "location")
    UserProfile toEntity(UserProfileDTO userProfileDTO);

    default UserProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        return userProfile;
    }
}
