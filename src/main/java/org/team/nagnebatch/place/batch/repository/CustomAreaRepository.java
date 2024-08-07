package org.team.nagnebatch.place.batch.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.team.nagnebatch.place.domain.Area;

@Repository
public class CustomAreaRepository {
    @Autowired
    private EntityManagerFactory emf;

    public Integer save(Area area) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(area);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        return area.getAreaCode();
    }
}
