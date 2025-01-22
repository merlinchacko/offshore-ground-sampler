package com.fugro.ogs.domain.sample;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SampleMapper
{
    SampleDto toSampleDto(Sample sample);

    Sample toSample(SampleDto sampleDto);

    @Mapping(target = "sampleId", ignore = true)
    void updateSampleFromDto(SampleDto sampleDto, @MappingTarget Sample existingSample);
}

