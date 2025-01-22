package com.fugro.ogs.domain.sample;

import java.time.LocalDate;

import com.fugro.ogs.domain.location.LocationDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleDto
{
    private Long id;
    private LocationDto location;
    @NotNull(message = "Date collected must not be null")
    @PastOrPresent(message = "Date collected must not be in the future")
    private LocalDate dateCollected;
    private double unitWeight;
    private double waterContent;
    private double shearStrength;

}
