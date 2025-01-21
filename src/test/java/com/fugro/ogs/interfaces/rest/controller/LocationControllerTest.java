package com.fugro.ogs.interfaces.rest.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fugro.ogs.domain.location.LocationDto;
import com.fugro.ogs.domain.location.LocationService;


@WebMvcTest(LocationController.class)
class LocationControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private LocationService locationService;
    @Autowired
    private ObjectMapper objectMapper;

    private LocationDto locationDto;
    private List<LocationDto> locationDtos;

    @BeforeEach
    void setUp()
    {
        locationDto = new LocationDto(1L, "Amsterdam");
        locationDtos = List.of(locationDto);
    }

    @Test
    void shouldReturnAllLocations() throws Exception
    {
        // given
        when(locationService.getAllLocations()).thenReturn(locationDtos);

        // when
        mockMvc.perform(get("/api/v1/locations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Amsterdam"));

        // then
        verify(locationService, times(1)).getAllLocations();
    }

    @Test
    void shouldCreateNewLocation() throws Exception
    {
        // given
        when(locationService.createLocation(any(LocationDto.class))).thenReturn(locationDto);

        // when
        mockMvc.perform(post("/api/v1/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Amsterdam"));

        // then
        verify(locationService, times(1)).createLocation(any(LocationDto.class));
    }

    @Test
    void shouldDeleteLocationById() throws Exception
    {
        // given
        doNothing().when(locationService).deleteLocation(1L);

        // when
        mockMvc.perform(delete("/api/v1/locations/{id}", 1L))
            .andExpect(status().isNoContent());

        // then
        verify(locationService, times(1)).deleteLocation(1L);
    }
}
