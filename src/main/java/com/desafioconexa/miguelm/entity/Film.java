package com.desafioconexa.miguelm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Film")
public class Film {

    @Column
    private String title;

    @Id
    @Column
    private String episodeId;

    @Column
    private String director;

    @Column
    private Date releaseDate;
}
