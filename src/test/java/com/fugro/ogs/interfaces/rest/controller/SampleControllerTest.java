package com.fugro.ogs.interfaces.rest.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fugro.ogs.domain.location.LocationDto;
import com.fugro.ogs.domain.sample.SampleDto;
import com.fugro.ogs.domain.sample.SampleService;


@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SampleService sampleService;
    @Autowired
    private ObjectMapper objectMapper;

    private SampleDto sampleDto;
    private List<SampleDto> sampleDtos;

    @BeforeEach
    void setUp()
    {
        sampleDto = new SampleDto(1L, new LocationDto(1L, "Amsterdam"), LocalDate.now().minusDays(1), 20.0, 100.0, 56.0);
        sampleDtos = List.of(sampleDto);
    }

    @Test

    void shouldReturnAllSamples() throws Exception
    {
        // given
        when(sampleService.getAllSamples()).thenReturn(sampleDtos);

        // when
        mockMvc.perform(get("/api/samples"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].location.name").value("Amsterdam"))
            .andExpect(jsonPath("$[0].waterContent").value(100.0));

        // then
        verify(sampleService, times(1)).getAllSamples();
    }

    @Test
    @WithMockUser(username = "${security.user.username}", password = "${security.user.password}", roles = "${security.user.role}")
    void shouldCreateNewSample() throws Exception
    {
        // given
        when(sampleService.createSample(any(SampleDto.class))).thenReturn(sampleDto);

        // when
        mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.location.name").value("Amsterdam"));

        // then
        verify(sampleService, times(1)).createSample(any(SampleDto.class));
    }

    @Test
    @WithMockUser(username = "${security.user.username}", password = "${security.user.password}", roles = "${security.user.role}")
    void shouldReturnBadRequestWhenDateCollectedIsInFuture() throws Exception
    {
        // given
        sampleDto.setDateCollected(LocalDate.now().plusDays(1));
        when(sampleService.createSample(any(SampleDto.class))).thenReturn(sampleDto);

        // when & then
        mockMvc.perform(post("/api/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.message").value("dateCollected: Date collected must not be in the future"));
    }

    @Test
    @WithMockUser(username = "${security.user.username}", password = "${security.user.password}", roles = "${security.user.role}")
    void shouldUpdateAnExistingSample() throws Exception
    {
        // given
        sampleDto.setShearStrength(50.0);
        when(sampleService.updateSample(anyLong(), any(SampleDto.class))).thenReturn(sampleDto);

        // when & then
        mockMvc.perform(put("/api/samples/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.location.name").value("Amsterdam"))
            .andExpect(jsonPath("$.shearStrength").value(50.0));
    }

    @Test
    @WithMockUser(username = "${security.user.username}", password = "${security.user.password}", roles = "${security.user.role}")
    void shouldDeleteSampleById() throws Exception
    {
        // given
        doNothing().when(sampleService).deleteSample(1L);

        // when
        mockMvc.perform(delete("/api/samples/{id}", 1L))
            .andExpect(status().isNoContent());

        // then
        verify(sampleService, times(1)).deleteSample(1L);
    }
}
