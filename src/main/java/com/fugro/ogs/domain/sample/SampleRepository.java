package com.fugro.ogs.domain.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query("SELECT AVG(s.waterContent) FROM Sample s")
    Double getAverageWaterContent();
}
