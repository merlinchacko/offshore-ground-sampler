package com.fugro.ogs.interfaces.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fugro.ogs.domain.location.LocationDto;
import com.fugro.ogs.domain.location.LocationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController
{
    private final LocationService locationService;

    @GetMapping
    public List<LocationDto> getAllLocations()
    {
        return locationService.getAllLocations();
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody final LocationDto locationDto)
    {
        return new ResponseEntity<>(locationService.createLocation(locationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable final Long id)
    {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
