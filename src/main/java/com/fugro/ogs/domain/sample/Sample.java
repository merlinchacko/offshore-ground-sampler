package com.fugro.ogs.domain.sample;

import java.time.LocalDate;

import com.fugro.ogs.domain.location.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SAMPLE")
@Getter
@Setter
public class Sample
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(nullable = false)
    private LocalDate dateCollected;

    @Column(nullable = false)
    private double unitWeight;

    @Column(nullable = false)
    private double waterContent;

    @Column(nullable = false)
    private double shearStrength;

}
