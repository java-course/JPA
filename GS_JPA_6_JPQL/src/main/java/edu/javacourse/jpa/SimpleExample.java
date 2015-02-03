package edu.javacourse.jpa;

import edu.javacourse.jpa.entity.Author;
import edu.javacourse.jpa.entity.Book;
import edu.javacourse.jpa.entity.City;
import edu.javacourse.jpa.entity.Region;
import edu.javacourse.jpa.manager.RegionManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SimpleExample {

    public static void main(String[] args) {
        RegionManager rm = new RegionManager();
        rm.init();
        EntityManager em = rm.getEntityManager();


        // cities where region name contains 'o' character
//        Query query = em.createQuery("select c from City c where c.region.regionName like :r");
//        query.setParameter("r", "%o%");
//        printCities(query.getResultList());
//
//        Query query2 = em.createQuery("select c from City c where c.region.regionName in ('Voronezh', 'Moscow')");
//        printCities(query2.getResultList());
//
//        Query query3 = em.createQuery("select c from Region c where c.cityList is empty");
//        printRegions(query3.getResultList());
//
//        Query query4 = em.createQuery("select a from Author a where size(a.bookList) > 2 ");
//        printAuthors(query4.getResultList());
//
        //Query query5 = em.createQuery("select b from Book b, Author a where b member of a.bookList and a.authorName =:Author");
        Query query5 = em.createQuery("select b from Book b inner join b.authorList al where al.authorName=:Author");
        query5.setParameter("Author", "Small Author");
        printBooks(query5.getResultList());


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
