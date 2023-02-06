package org.ajakz.elker.dao;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "bugle")
public class Bugle {

    @Id
    @Column(name = "bugle_id")
    private UUID bugleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Elker elker;

    @Column(name = "bugle")
    private String bugle;

    @CreationTimestamp
    @Column(name = "create_date", insertable = true, updatable = false)
    private Instant createDate;
}
