package com.fugro.ogs.domain.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fugro.ogs.domain.location.Location;
import com.fugro.ogs.domain.location.LocationDto;

class SampleServiceTest
{
    @Mock
    private SampleRepository sampleRepository;
    @Mock
    private SampleMapper sampleMapper;
    @Mock
    private SampleValidator sampleValidator;
    @InjectMocks
    private SampleServiceImpl sampleService;

    private Sample sample1;
    private Sample sample2;
    private SampleDto sampleDto1;
    private SampleDto sampleDto2;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        final Location location1 = new Location();
        location1.setLocationId(1L);
        location1.setName("Amsterdam");

        final Location location2 = new Location();
        location2.setLocationId(2L);
        location2.setName("Rotterdam");

        sample1 = new Sample();
        sample1.setLocation(location1);
        sample1.setDateCollected(LocalDate.now().minusDays(1));
        sample1.setUnitWeight(50.0);
        sample1.setWaterContent(100.0);
        sample1.setShearStrength(100.0);

        sample2 = new Sample();
        sample2.setLocation(location2);
        sample2.setDateCollected(LocalDate.now().minusDays(5));
        sample2.setUnitWeight(15.0);
        sample2.setWaterContent(21.0);
        sample2.setShearStrength(10.0);

        sampleDto1 = new SampleDto(1L, new LocationDto(1L, "Amsterdam"), LocalDate.now().minusDays(1), 50.0, 100.0, 100.0);
        sampleDto2 = new SampleDto(2L, new LocationDto(2L, "Rotterdam"), LocalDate.now().minusDays(5), 15.0, 21.0, 10.0);
    }

    @Test
    void shouldReturnAllSamples()
    {
        // given
        when(sampleRepository.findAll()).thenReturn(List.of(sample1, sample2));
        when(sampleMapper.toSampleDto(sample1)).thenReturn(sampleDto1);
        when(sampleMapper.toSampleDto(sample2)).thenReturn(sampleDto2);

        // when
        final List<SampleDto> samples = sampleService.getAllSamples();

        // then
        assertNotNull(samples);
        assertEquals(2, samples.size());
        assertEquals("Amsterdam", samples.get(0).getLocation().name());
        assertEquals("Rotterdam", samples.get(1).getLocation().name());
        assertEquals(50.0, samples.get(0).getUnitWeight());
        assertEquals(15.0, samples.get(1).getUnitWeight());
        verify(sampleRepository, times(1)).findAll();
        verify(sampleMapper, times(1)).toSampleDto(sample1);
        verify(sampleMapper, times(1)).toSampleDto(sample2);
    }

    @Test
    void shouldReturnEmptyListWhenNoSamples()
    {
        // given
        when(sampleRepository.findAll()).thenReturn(List.of());

        // when
        final List<SampleDto> samples = sampleService.getAllSamples();

        // then
        assertNotNull(samples);
        assertTrue(samples.isEmpty());
        verify(sampleRepository, times(1)).findAll();
        verify(sampleMapper, never()).toSampleDto(any(Sample.class));
    }

    @Test
    void shouldCreateNewSample()
    {
        // given
        when(sampleMapper.toSample(sampleDto1)).thenReturn(sample1);
        when(sampleRepository.save(sample1)).thenReturn(sample1);
        when(sampleMapper.toSampleDto(sample1)).thenReturn(sampleDto1);
        doNothing().when(sampleValidator).validateUnitWeight(15.0);
        doNothing().when(sampleValidator).validateWaterContent(100.0);
        doNothing().when(sampleValidator).validateShearStrength(100.0);

        // when
        final SampleDto createdSample = sampleService.createSample(sampleDto1);

        // then
        assertNotNull(createdSample);
        assertEquals("Amsterdam", createdSample.getLocation().name());
        verify(sampleMapper, times(1)).toSample(sampleDto1);
        verify(sampleRepository, times(1)).save(sample1);
        verify(sampleMapper, times(1)).toSampleDto(sample1);
        verify(sampleValidator).validateUnitWeight(15.0);
        verify(sampleValidator).validateWaterContent(100.0);
        verify(sampleValidator).validateShearStrength(100.0);
    }

    @Test
    void shouldNotCreateNewSampleWhenThereIsThresholdBreach()
    {
        // given
        when(sampleMapper.toSample(sampleDto1)).thenReturn(sample1);
        doThrow(new InvalidSampleInputException("Unit weight must be between 12.0 and 26.0 kN/m³. Provided: 50.0")).when(sampleValidator).validateUnitWeight(50.0);
        doNothing().when(sampleValidator).validateWaterContent(100.0);
        doNothing().when(sampleValidator).validateShearStrength(100.0);

        // when
        final InvalidSampleInputException thrown = assertThrows(InvalidSampleInputException.class, () -> sampleService.createSample(sampleDto1));

        // then
        assertEquals("Unit weight must be between 12.0 and 26.0 kN/m³. Provided: 50.0", thrown.getMessage());
    }

    @Test
    void shouldDeleteSample()
    {
        // when
        sampleService.deleteSample(1L);

        // then
        verify(sampleRepository, times(1)).deleteById(1L);
    }
}
