package edu.javacourse.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.Type;

/**
 * Простой пример для interceptor
 *
 * @author ASaburov
 * @author Georgy Gobozov
 */
public class HibernateSimple {

    static HibernateSimple hs = new HibernateSimple();

    static Serializable id = null;

    public static void main(String[] args) {
            create();

    }

    private static void create() {
        System.out.println("==============CREATE=================");
        // Create hibernate session
        Session session = hs.getSessionFactory().openSession();
        // begin transaction
        session.beginTransaction();
        // create object
        Region region = new Region("Len Oblast!");



        //save object
        List<City> cities = new LinkedList<City>();
        cities.add(new City(123, "Vyborg"));
        cities.add(new City(124, "Priozersk"));
        region.setCityList(cities);

        session.save(region);
        session.getTransaction().commit();
        session.close();
    }




    private static void update() {
        System.out.println("==============UPDATE=================");
        Session session = hs.getSessionFactory().openSession();
        session.beginTransaction();
        City city = (City) session.load(City.class, id);
        city.setCityName("nefteugansk");
        session.saveOrUpdate(city);
        System.out.println("city = " + city);

        session.getTransaction().commit();
        session.close();

    }


    private static void delete() {
        System.out.println("==============DELETE=================");
        Session session = hs.getSessionFactory().openSession();
        session.beginTransaction();
        City city = (City) session.load(City.class, id);

        session.delete(city);

        session.getTransaction().commit();
        session.close();

    }



    private SessionFactory getSessionFactory() {
        Configuration cfg = new Configuration();
        return cfg.configure().buildSessionFactory();
    }
}
