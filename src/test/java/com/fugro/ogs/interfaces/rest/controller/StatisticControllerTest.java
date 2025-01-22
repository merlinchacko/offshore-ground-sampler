package com.fugro.ogs.interfaces.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fugro.ogs.domain.sample.StatisticService;


@WebMvcTest(StatisticController.class)
class StatisticControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private StatisticService statisticService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnStatisticForWaterContent() throws Exception
    {
        // given
        when(statisticService.calculateAverageWaterContent()).thenReturn(15.0);

        // when & then
        mockMvc.perform(get("/api/v1/statistics/water-content"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(15.0));
    }
}
