package com.fugro.ogs.interfaces.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fugro.ogs.domain.sample.SampleDto;
import com.fugro.ogs.domain.sample.SampleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/samples")
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
        final SampleDto created = service.createSample(sampleDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleDto> updateSample(@PathVariable final Long id, @Valid @RequestBody final SampleDto sampleDto)
    {
        final SampleDto updated = service.updateSample(id, sampleDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable final Long id)
    {
        service.deleteSample(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
