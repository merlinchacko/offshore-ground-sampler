package com.fugro.ogs.domain.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class StatisticServiceTest
{
    @Mock
    private SampleRepository sampleRepository;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void shouldReturnAverageWaterContent()
    {
        // given
        when(sampleRepository.getAverageWaterContent()).thenReturn(15.0);

        // when
        final Double average = statisticService.calculateAverageWaterContent();

        // then
        assertEquals(15.0, average);
    }
}
