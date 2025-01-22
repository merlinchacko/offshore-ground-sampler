package com.fugro.ogs.domain.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


/**
 * Configuration properties for default sample thresholds
 */
@Component
@ConfigurationProperties("ogs.sample-threshold")
@Getter
@Setter
public class SampleThresholdProperties
{
    private double minWaterContentPercent;
    private double maxWaterContentPercent;
    private double minUnitWeightKNPerM3;
    private double maxUnitWeightKNPerM3;
    private double minShearStrengthKPa;
    private double maxShearStrengthKPa;

}
