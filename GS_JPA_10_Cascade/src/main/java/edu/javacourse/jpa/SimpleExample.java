package edu.javacourse.jpa;

import edu.javacourse.jpa.entity.Author;
import edu.javacourse.jpa.entity.Book;
import edu.javacourse.jpa.entity.City;
import edu.javacourse.jpa.entity.Region;
import edu.javacourse.jpa.manager.RegionManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

public class SimpleExample {

    public static void main(String[] args) {
        RegionManager rm = new RegionManager();
        rm.init();
        EntityManager em = rm.getEntityManager();

        //test1(em);
        test2(em);



    }

    private static void test2(EntityManager em) {
        // create city and region
        em.getTransaction().begin();
        // region should have @OneToMany( mappedBy = "region", fetch = FetchType.LAZY)
        Region region = new Region("New region 3.02.2015");
        em.persist(region);


        City city = new City("New city 03.02.2015");
        city.setRegion(region);
        em.persist(city);
        em.getTransaction().commit();

        int id = region.getRegionId();
        System.out.println("id = " + id);

        // take a look in database region and city be sure that new values exists
         // take a look on new city region_id
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // take a look on new city region_id again, must change

        em.getTransaction().begin();
        region.setRegionId(region.getRegionId() + 1000);
        System.out.println("new id  = " + region.getRegionId());
        em.merge(region);
        em.getTransaction().commit();
        // then change to @OneToMany( mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    }

    private static void test1(EntityManager em) {
        // create city and region
        em.getTransaction().begin();
        // region should have @OneToMany( mappedBy = "region", fetch = FetchType.LAZY)
        Region region = new Region("Test region 2015");
        em.persist(region);
        City city = new City("Test city 2015");
        city.setRegion(region);
        em.persist(city);
        em.getTransaction().commit();

        // take a look in database region and city be sure that new values exists

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //try to delete region
        em.getTransaction().begin();
        em.remove(region);
        em.getTransaction().commit();
        // then change to @OneToMany( mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    }


    private static void test3(EntityManager em,  CriteriaBuilder cb) {
        CriteriaQuery<Region> criteriaQuery = cb.createQuery(Region.class);
        Root<Region> root = criteriaQuery.from(Region.class);
        criteriaQuery.select(root);


        Expression<Collection<City>> cities = root.get("cityList");

        criteriaQuery.where(cb.greaterThan(cb.size(cities), 1));

        // create query via EM
        Query query = em.createQuery(criteriaQuery);
        List<Region> regions = query.getResultList();
        printRegions(regions);
    }

    private static void test2(EntityManager em,  CriteriaBuilder cb) {

        CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);
        criteriaQuery.where(cb.equal(root.get("bookId"), 1));
        TypedQuery<Book> q = em.createQuery(criteriaQuery);
        List<Book> books = q.getResultList();
        printBooks(books);
    }

    private static void test1(EntityManager em,  CriteriaBuilder cb) {
        CriteriaQuery<Region> criteriaQuery = cb.createQuery(Region.class);
        Root<Region> root = criteriaQuery.from(Region.class);
        criteriaQuery.select(root);
        TypedQuery<Region> q = em.createQuery(criteriaQuery);
        List<Region> allRegions = q.getResultList();
        printRegions(allRegions);
    }

    private static void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book.getBookName());
        }
    }

    private static void printAuthors(List<Author> authors) {
        for (Author author : authors) {
            System.out.println(author.getAuthorName());
        }
        System.out.println("============================================");
    }

    private static void printCities(List<City> cities) {
        for (City city : cities) {
            System.out.println(city.getRegion().getRegionName() + " " + city.getCityName());
        }
        System.out.println("============================================");
    }

    private static void printRegions(List<Region> regions) {
        for (Region region : regions) {
            System.out.println(region.getRegionName());
        }
        System.out.println("============================================");
    }


}
