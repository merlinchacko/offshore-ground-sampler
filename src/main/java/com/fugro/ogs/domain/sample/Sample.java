package com.fugro.ogs.domain.sample;

import java.time.LocalDate;

import com.fugro.ogs.domain.location.Location;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Sample
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;

    @ManyToOne()
    @JoinColumn(name = "LOCATIONID", nullable = false)
    private Location location;

    @PastOrPresent(message = "Date collected must not be in the future")
    private LocalDate dateCollected;

    private double unitWeight;

    private double waterContent;

    private double shearStrength;

}
