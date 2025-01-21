package com.fugro.ogs.domain.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class LocationRepositoryIntegrationTest
{
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldCreateNewLocationAndReturnLocation()
    {
        // given
        final Location newLocation = new Location();
        newLocation.setName("Amsterdam");

        // when
        locationRepository.save(newLocation);
        final Location location = locationRepository.findById(newLocation.getLocationId()).orElse(null);

        // then
        assertNotNull(location);
        assertEquals("Amsterdam", location.getName());
    }
}
