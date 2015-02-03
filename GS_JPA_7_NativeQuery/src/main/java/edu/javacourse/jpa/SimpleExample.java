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



        Query query = em.createNativeQuery("select * from jc_city", City.class);
        printCities(query.getResultList());

        Query query2 = em.createNativeQuery("SELECT  * FROM jc_author AS a JOIN jc_book_author AS ba ON a.author_id = ba.author_id  JOIN jc_book AS  b  ON b.book_id = ba.book_id AND b.book_name=?1", Author.class);
        query2.setParameter(1, "Java for beginners");
        printAuthors(query2.getResultList());


        // select regions with cities size greater than one
        Query query3 = em.createNativeQuery("SELECT * FROM jc_region AS reg JOIN jc_city AS cit ON reg.region_id = cit.region_id GROUP BY region_name HAVING COUNT(cit.region_id) > 1", Region.class);
        printRegions(query3.getResultList());




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
