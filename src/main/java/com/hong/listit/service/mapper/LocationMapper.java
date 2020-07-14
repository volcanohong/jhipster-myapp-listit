package com.hong.listit.service.mapper;


import com.hong.listit.domain.*;
import com.hong.listit.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

//    @Mapping(source = "city.id", target = "cityId")
    LocationDTO toDto(Location location);

//    @Mapping(source = "cityId", target = "city")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
