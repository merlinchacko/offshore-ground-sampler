package com.fugro.ogs.domain.sample;

import java.util.List;



public interface SampleService
{
     List<SampleDto> getAllSamples();

     SampleDto createSample(SampleDto sampleDto);

     SampleDto updateSample(Long id, SampleDto sampleDto);

     void deleteSample(Long id) ;
}
