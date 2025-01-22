package com.fugro.ogs.domain.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fugro.ogs.domain.location.Location;
import com.fugro.ogs.domain.location.LocationRepository;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
class SampleRepositoryIntegrationTest
{
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldCreateNewSampleAndReturnSample()
    {
        // given
        final Location location = new Location();
        location.setName("Amsterdam");
        final Location savedLocation = locationRepository.save(location);

        final Sample newSample = new Sample();
        newSample.setLocation(savedLocation);
        newSample.setDateCollected(LocalDate.now().minusDays(1));
        newSample.setUnitWeight(15.0);
        newSample.setWaterContent(100.0);
        newSample.setShearStrength(100.0);

        // when
        sampleRepository.save(newSample);
        final Sample sample = sampleRepository.findById(newSample.getSampleId()).orElse(null);

        // then
        assertNotNull(sample);
        assertEquals("Amsterdam", sample.getLocation().getName());
    }

    @Test
    void shouldReturnAverageWaterContentFromAllSamples() {
        // given
        final Location location = new Location();
        location.setName("Amsterdam");
        final Location savedLocation = locationRepository.save(location);

        final Sample sample1 = new Sample();
        sample1.setLocation(savedLocation);
        sample1.setDateCollected(LocalDate.now().minusDays(1));
        sample1.setUnitWeight(15.0);
        sample1.setWaterContent(100.0);
        sample1.setShearStrength(100.0);
        sampleRepository.save(sample1);

        final Sample sample2 = new Sample();
        sample2.setLocation(savedLocation);
        sample2.setDateCollected(LocalDate.now().minusDays(1));
        sample2.setUnitWeight(15.0);
        sample2.setWaterContent(100.0);
        sample2.setShearStrength(100.0);
        sampleRepository.save(sample2);

        // when
        final Double average = sampleRepository.getAverageWaterContent();

        //then
        assertEquals(55.625, average, 0.001);
    }
}
