package com.epam.training.ticketservice.core.screening.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
