package com.markzfilter.contactmgr;

import com.markzfilter.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Application {

    // Hold a reusable reference to a SessionFactory object
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Donald", "Trump")
                .withEmail("dt@whitehouse.gov")
                .withPhone(2025550666L).build();

        save(contact);



        for (Contact c : fetchAllContacts()) {
            System.out.println(c.toString());
        }

    }

    private static List<Contact> fetchAllContacts() {
        // Open Session
        Session session = sessionFactory.openSession();

        // Create Criteria Object
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Contact> criteria = criteriaBuilder.createQuery(Contact.class);

        Root<Contact> root = criteria.from(Contact.class);
        criteria.select(root);

        // Get a List of Contact objects according to Criteria object
        List<Contact> contacts = session.createQuery(criteria).getResultList();

        // Close the Session
        session.close();

        return contacts;
    }

    private static void save(Contact contact) {
        // Open Session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        // Add one more dependency to the gradle.build file
        // compile 'javax.transaction:jta:1.1'
        session.beginTransaction();

        // Use the Session to save the contact
        session.save(contact);

        // Commit the Transaction
        session.getTransaction().commit();

        // Close the Session
        session.close();
    }
}
