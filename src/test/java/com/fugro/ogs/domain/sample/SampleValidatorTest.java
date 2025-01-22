package com.fugro.ogs.domain.sample;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class SampleValidatorTest
{
    @Mock
    private SampleThresholdProperties sampleThresholdProperties;

    @InjectMocks
    private SampleValidator sampleValidator;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
        "12.0, 5.0, 150.0, true",
        "2.0, 5.0, 150.0, false",
        "162.0, 5.0, 150.0, false",
    })
    void shouldValidateWaterContentValueBasedOnThreshold(final double waterContent, final double min, final double max, final boolean isValid)
    {
        // given
        when(sampleThresholdProperties.getMinWaterContentPercent()).thenReturn(min);
        when(sampleThresholdProperties.getMaxWaterContentPercent()).thenReturn(max);

        // when & then
        if (isValid)
        {
            assertDoesNotThrow(() -> sampleValidator.validateWaterContent(waterContent));
        }
        else
        {
            final InvalidSampleInputException thrown = assertThrows(InvalidSampleInputException.class, () -> sampleValidator.validateWaterContent(waterContent));
            assertEquals("Water content must be between 5.0 and 150.0. Provided: " + waterContent, thrown.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "15.0, 12.0, 26.0, true",
        "2.0, 12.0, 26.0, false",
        "50.0, 12.0, 26.0, false",
    })
    void shouldValidateUnitWeightValueBasedOnThreshold(final double unitWeight, final double min, final double max, final boolean isValid)
    {
        // given
        when(sampleThresholdProperties.getMinUnitWeightKNPerM3()).thenReturn(min);
        when(sampleThresholdProperties.getMaxUnitWeightKNPerM3()).thenReturn(max);

        // when & then
        if (isValid)
        {
            assertDoesNotThrow(() -> sampleValidator.validateUnitWeight(unitWeight));
        }
        else
        {
            final InvalidSampleInputException thrown = assertThrows(InvalidSampleInputException.class, () -> sampleValidator.validateUnitWeight(unitWeight));
            assertEquals("Unit weight must be between 12.0 and 26.0 kN/m³. Provided: " + unitWeight, thrown.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "12.0, 2.0, 1000.0, true",
        "1.9, 2.0, 1000.0, false",
        "1620.0, 2.0, 1000.0, false",
    })
    void shouldValidateShearStrengthValueBasedOnThreshold(final double shearStrength, final double min, final double max, final boolean isValid)
    {
        // given
        when(sampleThresholdProperties.getMinShearStrengthKPa()).thenReturn(min);
        when(sampleThresholdProperties.getMaxShearStrengthKPa()).thenReturn(max);

        // when & then
        if (isValid)
        {
            assertDoesNotThrow(() -> sampleValidator.validateShearStrength(shearStrength));
        }
        else
        {
            final InvalidSampleInputException thrown = assertThrows(InvalidSampleInputException.class, () -> sampleValidator.validateShearStrength(shearStrength));
            assertEquals("Shear strength must be between 2.0 and 1000.0 kPa. Provided: " + shearStrength, thrown.getMessage());
        }
    }

}
