package com.fugro.ogs.interfaces.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fugro.ogs.domain.sample.SampleDto;
import com.fugro.ogs.domain.sample.SampleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/samples")
@RequiredArgsConstructor
public class SampleController
{

    private final SampleService service;

    @GetMapping
    public List<SampleDto> getAllSamples()
    {
        return service.getAllSamples();
    }

    @PostMapping
    public ResponseEntity<SampleDto> createSample(@Valid @RequestBody final SampleDto sampleDto)
    {
        return new ResponseEntity<>(service.createSample(sampleDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable final Long id)
    {
        service.deleteSample(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
