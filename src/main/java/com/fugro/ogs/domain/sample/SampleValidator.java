package com.fugro.ogs.domain.sample;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class SampleValidator
{
    private final SampleThresholdProperties sampleThresholdProperties;

    public void validateWaterContent(final double waterContent)
    {
        final double min = sampleThresholdProperties.getMinWaterContentPercent();
        final double max = sampleThresholdProperties.getMaxWaterContentPercent();

        if (waterContent < min || waterContent > max)
        {
            throw new InvalidSampleInputException(
                String.format("Water content must be between %.1f and %.1f. Provided: %.1f", min, max, waterContent));
        }
    }

    public void validateUnitWeight(final double unitWeight)
    {
        final double min = sampleThresholdProperties.getMinUnitWeightKNPerM3();
        final double max = sampleThresholdProperties.getMaxUnitWeightKNPerM3();

        if (unitWeight < min || unitWeight > max)
        {
            throw new InvalidSampleInputException(
                String.format("Unit weight must be between %.1f and %.1f kN/m³. Provided: %.1f", min, max, unitWeight));
        }
    }

    public void validateShearStrength(final double shearStrength)
    {
        final double min = sampleThresholdProperties.getMinShearStrengthKPa();
        final double max = sampleThresholdProperties.getMaxShearStrengthKPa();

        if (shearStrength < min || shearStrength > max)
        {
            throw new InvalidSampleInputException(
                String.format("Shear strength must be between %.1f and %.1f kPa. Provided: %.1f", min, max, shearStrength));
        }
    }
}

