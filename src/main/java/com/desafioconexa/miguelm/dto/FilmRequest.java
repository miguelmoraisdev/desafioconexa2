package com.desafioconexa.miguelm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FilmRequest", description = "Schema of a FilmRequest object")
public class FilmRequest {

    @NotBlank(message = "Required field")
    @JsonProperty(value = "title")
    private String title;

    @NotBlank(message = "Required field")
    @JsonProperty(value = "episode_id")
    private String episodeId;

    @NotBlank(message = "Required field")
    @JsonProperty(value = "director")
    private String director;

    @NotNull(message = "Required field")
    @Schema(example = "dd/MM/yyyy")
    @JsonProperty(value = "release_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date releaseDate;

}
