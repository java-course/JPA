package edu.javacourse.jpa.manager;

import edu.javacourse.jpa.entity.Region;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RegionManager {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("unitEclipseLink", new java.util.HashMap());
        System.out.println(entityManagerFactory.getClass().getSimpleName());
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println(entityManager.getClass().getSimpleName());
    }

    public List<Region> getRegionList() {
        return entityManager.createQuery("select r from Region r").getResultList();
    }

    public List<Region> getRegionList2() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Region> cq = cb.createQuery(Region.class);
        return entityManager.createQuery(cq).getResultList();
    }

}
