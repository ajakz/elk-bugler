package org.ajakz.elker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ajakz.elker.dao.Elker;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElkerData {

    @Schema(description = "The user ID of the Elker.")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID elkerId;

    @Schema(description = "The username of the Elker.")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String username;

    @Schema(description = "If the requesting user is following this Elker")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean following;

    public static ElkerData fromElkerEntity(Elker elker) {
        var builder = ElkerData.builder();
        return builder.elkerId(elker.getId())
                .username(elker.getUsername())
                .build();
    }

    public static List<ElkerData> fromProfileEntityList(List<Elker> elkerEntities) {
        return elkerEntities.stream().map(ElkerData::fromElkerEntity).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElkerData elkerData = (ElkerData) o;
        return getElkerId().equals(elkerData.getElkerId()) && getUsername().equals(elkerData.getUsername());
    }

    @Override
    public String toString() {
        return "ElkerData{" +
                "elkerId=" + elkerId +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElkerId(), getUsername());
    }
}
