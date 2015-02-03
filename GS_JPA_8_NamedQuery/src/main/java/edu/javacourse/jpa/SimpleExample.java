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


        Query query = em.createNamedQuery("Book.ByName");
        query.setParameter("name", "Java for beginners");
        Book book = (Book) query.getSingleResult();
        System.out.println("book = " + book);

        Query query2 = em.createNamedQuery("Book.ByAuthor");
        query2.setParameter("Author", "Small Author");
        List<Book> books = query2.getResultList();
        printBooks(books);

        Query query3 = em.createNamedQuery("Author.ById");
        query3.setParameter("id", 1);
        Author author = (Author) query3.getSingleResult();
        System.out.println("author = " + author);


        Query query4 = em.createNamedQuery("Region.Count");
        Long count = (Long)query4.getSingleResult();
        System.out.println("count = " + count);

        Query query5 = em.createNamedQuery("Region.DelebeById");
        em.getTransaction().begin();
        query5.setParameter("id", count);
        query5.executeUpdate();
        em.getTransaction().commit();







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
