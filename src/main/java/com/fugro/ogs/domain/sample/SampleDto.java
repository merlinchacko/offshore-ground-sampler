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
    @NotNull(message = "Location must not be null")
    private LocationDto location;
    @NotNull(message = "Date collected must not be null")
    @PastOrPresent(message = "Date collected must not be in the future")
    private LocalDate dateCollected;
    @NotNull(message = "Unit Weight must not be null")
    private double unitWeight;
    @NotNull(message = "Water content must not be null")
    private double waterContent;
    @NotNull(message = "Shear strength must not be null")
    private double shearStrength;

}
