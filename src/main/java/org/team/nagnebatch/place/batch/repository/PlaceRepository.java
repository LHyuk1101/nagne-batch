package org.team.nagnebatch.place.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.nagnebatch.place.domain.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
  boolean existsByContentId(String contentId);
}
