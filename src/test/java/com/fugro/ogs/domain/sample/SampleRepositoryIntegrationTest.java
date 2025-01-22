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
}
