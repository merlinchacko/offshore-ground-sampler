package com.fugro.ogs.domain.sample;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService
{

    private final SampleRepository repository;
    private final SampleMapper sampleMapper;
    private final SampleValidator sampleValidator;

    @Override
    public List<SampleDto> getAllSamples()
    {
        final List<Sample> samples = repository.findAll();
        return samples.stream()
            .map(sampleMapper::toSampleDto)
            .toList();
    }

    @Override
    public SampleDto createSample(final SampleDto sampleDto)
    {
        checkSampleThresholds(sampleDto.getWaterContent(), sampleDto.getUnitWeight(), sampleDto.getShearStrength());
        final Sample sample = sampleMapper.toSample(sampleDto);
        final Sample savedSample = repository.save(sample);

        return sampleMapper.toSampleDto(savedSample);
    }

    private void checkSampleThresholds(final double waterContent, final double unitWeight, final double shearStrength)
    {
        sampleValidator.validateWaterContent(waterContent);
        sampleValidator.validateUnitWeight(unitWeight);
        sampleValidator.validateShearStrength(shearStrength);
    }

    @Override
    public void deleteSample(final Long id)
    {
        repository.deleteById(id);
    }

}
