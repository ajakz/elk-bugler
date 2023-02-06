package org.ajakz.elker.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "elk")
public class Elker {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @ManyToMany
//    @Cascade({
//            CascadeType.PERSIST
//    })
    @JoinTable(name = "bonds", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "following"))
    private Set<Elker> following;

    @ManyToMany(mappedBy = "following")
    private Set<Elker> followers;

    public void addFollowing(Elker elker) {
        this.following.add(elker);
        elker.getFollowers().add(this);
    }

    public void removeFollowing(Elker elker) {
        following.remove(elker);
        elker.getFollowers().remove(this);
    }

    public Set<UUID> getFollowingIds() {
        return following.stream().map(Elker::getId).collect(Collectors.toSet());
    }
}
