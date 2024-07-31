package org.team.nagnebatch.place.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team.nagnebatch.place.batch.market.domain.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

}
