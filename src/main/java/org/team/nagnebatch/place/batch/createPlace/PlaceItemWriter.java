package org.team.nagnebatch.place.batch.createPlace;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;
import org.team.nagnebatch.place.domain.Place;

public class PlaceItemWriter extends JpaItemWriter<Place> {

  @Override
  public void write(Chunk<? extends Place> items) {
    super.write(items);
  }
}
