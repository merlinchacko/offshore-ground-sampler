package com.fugro.ogs.domain.location;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService
{
    private final LocationRepository repository;
    private final LocationMapper locationMapper;

    @Override
    public List<LocationDto> getAllLocations()
    {
        final List<Location> locations = repository.findAll();
        return locations.stream()
            .map(locationMapper::toLocationDto)
            .toList();
    }

    @Override
    public LocationDto createLocation(final LocationDto locationDto)
    {
        final Location location = locationMapper.toLocation(locationDto);
        final Location savedLocation = repository.save(location);

        return locationMapper.toLocationDto(savedLocation);
    }

    @Override
    public void deleteLocation(final Long id)
    {
        repository.deleteById(id);
    }
}
