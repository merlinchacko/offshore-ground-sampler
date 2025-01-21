package com.fugro.ogs.domain.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class LocationServiceTest
{
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;
    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location1;
    private Location location2;
    private LocationDto locationDto1;
    private LocationDto locationDto2;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        location1 = new Location();
        location1.setLocationId(1L);
        location1.setName("Amsterdam");

        location2 = new Location();
        location2.setLocationId(2L);
        location2.setName("Rotterdam");

        locationDto1 = new LocationDto(1L, "Amsterdam");
        locationDto2 = new LocationDto(2L, "Rotterdam");
    }

    @Test
    void shouldReturnAllLocations()
    {
        // given
        when(locationRepository.findAll()).thenReturn(List.of(location1, location2));
        when(locationMapper.toLocationDto(location1)).thenReturn(locationDto1);
        when(locationMapper.toLocationDto(location2)).thenReturn(locationDto2);

        // when
        final List<LocationDto> locations = locationService.getAllLocations();

        // then
        assertNotNull(locations);
        assertEquals(2, locations.size());
        assertEquals("Amsterdam", locations.get(0).name());
        assertEquals("Rotterdam", locations.get(1).name());
        verify(locationRepository, times(1)).findAll();
        verify(locationMapper, times(1)).toLocationDto(location1);
        verify(locationMapper, times(1)).toLocationDto(location2);
    }

    @Test
    void shouldReturnEmptyListWhenNoLocations()
    {
        // given
        when(locationRepository.findAll()).thenReturn(List.of());

        // when
        final List<LocationDto> locations = locationService.getAllLocations();

        // then
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
        verify(locationRepository, times(1)).findAll();
        verify(locationMapper, never()).toLocationDto(any(Location.class));
    }

    @Test
    void shouldCreateNewLocation()
    {
        // given
        when(locationMapper.toLocation(locationDto1)).thenReturn(location1);
        when(locationRepository.save(location1)).thenReturn(location1);
        when(locationMapper.toLocationDto(location1)).thenReturn(locationDto1);

        // when
        final LocationDto createdLocation = locationService.createLocation(locationDto1);

        // then
        assertNotNull(createdLocation);
        assertEquals("Amsterdam", createdLocation.name());
        verify(locationMapper, times(1)).toLocation(locationDto1);
        verify(locationRepository, times(1)).save(location1);
        verify(locationMapper, times(1)).toLocationDto(location1);
    }

    @Test
    void shouldDeleteLocation()
    {
        // when
        locationService.deleteLocation(1L);

        // then
        verify(locationRepository, times(1)).deleteById(1L);
    }
}
