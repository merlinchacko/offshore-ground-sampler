package com.fugro.ogs.domain.sample;

import java.util.List;



public interface SampleService
{
     List<SampleDto> getAllSamples();

     SampleDto createSample(SampleDto sampleDto);

     void deleteSample(Long id) ;
}
