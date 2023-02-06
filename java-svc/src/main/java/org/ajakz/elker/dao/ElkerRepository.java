package org.ajakz.elker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ElkerRepository extends JpaRepository<Elker, UUID> {

    List<Elker> findFollowingById(UUID id);

    List<Elker> findFollowersById(UUID id);
}
