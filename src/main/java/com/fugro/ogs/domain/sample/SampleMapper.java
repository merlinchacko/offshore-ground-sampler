package com.fugro.ogs.domain.sample;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SampleMapper
{
    SampleDto toSampleDto(Sample sample);

    Sample toSample(SampleDto sampleDto);
}

