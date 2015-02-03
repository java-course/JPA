package edu.javacourse.jpa;

import edu.javacourse.jpa.entity.Region;
import edu.javacourse.jpa.manager.RegionManager;

import javax.persistence.EntityManager;
import java.util.List;

public class SimpleExample {

    public static void main(String[] args) {
        RegionManager rm = new RegionManager();
        rm.init();
        EntityManager em = rm.getEntityManager();

        selectRegions(em);

        Region region = new Region("Super Region");
        int id = createRegion(em, region);

        selectRegions(em);

        Region fromDb = getRegion(em, id);
        System.out.println("fromDb = " + fromDb);

        fromDb.setRegionName("Updated Super Region");

        fromDb = updateRegion(em, fromDb);
        System.out.println("fromDb updated = " + fromDb);

        removeRegion(em, fromDb);
        selectRegions(em);

        em.close();





    }

    private static int createRegion(EntityManager em, Region region) {
        em.getTransaction().begin();
        em.persist(region);
        em.getTransaction().commit();
        return region.getRegionId();
    }

    private static Region getRegion(EntityManager em, Integer id) {
        Region region = em.find(Region.class, id);
        return region;
    }

    private static Region updateRegion(EntityManager em, Region region) {
        em.getTransaction().begin();
        em.merge(region);
        em.getTransaction().commit();
        return region;
    }

    private static void removeRegion(EntityManager em, Region region) {
        em.getTransaction().begin();
        em.remove(region);
        em.getTransaction().commit();
    }

    private static void selectRegions(EntityManager em) {
        System.out.println("First Select ===>");
        List<Region> result = em.createQuery("select r from Region r").getResultList();
        for(Region r : result) {
            System.out.println(r);
        }
        System.out.println("=============================");
    }

}
