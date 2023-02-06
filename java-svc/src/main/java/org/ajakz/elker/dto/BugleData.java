package org.ajakz.elker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ajakz.elker.dao.Bugle;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BugleData {

    @Schema(description = "Unique ID of bugle generated at bugle creation.")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID bugleId;

    @Schema(description = "The user ID of the bugle creator.")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private UUID elkerId;

    @Schema(description = "The username of the bugle creator.")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @Schema(description = "Timestamp of bugle publication.")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String timestamp;

    @Schema(description = "The content of the bugle.")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String content;

    public static BugleData fromBugleEntity(Bugle bugle) {
        var builder = BugleData.builder();
        return builder.bugleId(bugle.getBugleId())
                .elkerId(bugle.getElker().getId())
                .username(bugle.getElker().getUsername())
                .timestamp(DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC).format(bugle.getCreateDate()))
                .content(bugle.getBugle())
                .build();
    }

    public static List<BugleData> fromBugleEntityList(List<Bugle> bugleEntities) {
        return bugleEntities.stream().map(BugleData::fromBugleEntity).toList();
    }
}
