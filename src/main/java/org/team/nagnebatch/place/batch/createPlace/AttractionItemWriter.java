package org.team.nagnebatch.place.batch.createPlace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.team.nagnebatch.place.domain.PlaceWrapper;

public class AttractionItemWriter implements ItemWriter<PlaceWrapper> {

  private final EntityManagerFactory emf;

  public AttractionItemWriter(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public void write(Chunk<? extends PlaceWrapper> chunk) throws Exception {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();

    try {
      transaction.begin();

      for (PlaceWrapper wrapper : chunk.getItems()) {
        em.persist(wrapper.getPlace());
        em.persist(wrapper.getPlaceImg());
      }

      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    } finally {
      em.close();
    }
  }
}
