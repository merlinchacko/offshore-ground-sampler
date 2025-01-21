package com.fugro.ogs.domain.location;

import java.util.List;

public interface LocationService
{
     List<LocationDto> getAllLocations();

     LocationDto createLocation(LocationDto locationDto);

     void deleteLocation(Long id) ;
}
