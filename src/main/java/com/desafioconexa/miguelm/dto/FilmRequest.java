package com.desafioconexa.miguelm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmRequest {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "episode_id")
    private String episodeId;

    @JsonProperty(value = "director")
    private String director;

    @JsonProperty(value = "release_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date releaseDate;

}
