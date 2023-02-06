package org.ajakz.elker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElkerBondData {

    @Schema(description = "The ID of the user.")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private UUID myId;

    @Schema(description = "The ID of the elker to follow/unfollow.")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private UUID theirId;
}
