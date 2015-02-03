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

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        //test1(em, criteriaBuilder);

        test2(em, criteriaBuilder);

        test3(em, criteriaBuilder);




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
