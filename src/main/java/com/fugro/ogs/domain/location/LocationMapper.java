package com.fugro.ogs.domain.location;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LocationMapper
{
    LocationDto toLocationDto(Location location);

    Location toLocation(LocationDto locationDto);
}

