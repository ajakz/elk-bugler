package org.ajakz.elker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BugleRepository extends JpaRepository<Bugle, UUID> {

    @Query(value = "select * from bugle b where b.user_id in (:userIds) order by b.create_date desc",
            nativeQuery = true)
    List<Bugle> findByUserIdIn(Set<UUID> userIds);
}
