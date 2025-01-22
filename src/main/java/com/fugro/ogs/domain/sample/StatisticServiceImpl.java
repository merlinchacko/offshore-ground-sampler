package com.fugro.ogs.domain.sample;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final SampleRepository sampleRepository;

    @Override
    public double calculateAverageWaterContent() {

        return sampleRepository.getAverageWaterContent();
    }
}
