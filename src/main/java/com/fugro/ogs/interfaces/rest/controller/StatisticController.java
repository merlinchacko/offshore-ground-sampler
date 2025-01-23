package com.fugro.ogs.interfaces.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fugro.ogs.domain.sample.StatisticService;


@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/water-content")
    public double getStatisticsForWaterContent() {

        return statisticService.calculateAverageWaterContent();
    }
}
