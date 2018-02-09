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

        int id = save(contact);

        // Display a list of contacts before the update
        System.out.println("Displaying list of contacts BEFORE update");
        for (Contact c : fetchAllContacts()) {
            System.out.println(c.toString());
        }

        // Get the Persisted Contact
        Contact c = fetchContactById(id);

        // Update the Contact
        c.setFirstName("Darth");

        // Persist the changes
        System.out.println("Updating contact...");
        update(c);

        // Display a list of contacts after the update
        System.out.println("Displaying list of contacts AFTER update");
        for (Contact persistedContact : fetchAllContacts()) {
            System.out.println(persistedContact.toString());
        }


        // Delete Contact
        // Display a list of contacts after the update
        System.out.println("Deleting Contacts...");
        for (Contact contactToDelete : fetchAllContacts()) {
            deleteContact(contactToDelete);
        }

        // Display a list of contacts after the update
        if (fetchAllContacts().size() != 0) {
            for (Contact eliminatedContact : fetchAllContacts()) {
                System.out.println(eliminatedContact.toString());
            }
        }
        else {
            System.out.println("All Contacts Deleted");
        }

    }

    private static void deleteContact(Contact contact) {

        // Open Session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Retrieve the persistent object (or null if not found)
        session.delete(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the Session
        session.close();

    }


    private static Contact fetchContactById(int id) {

        // Open Session
        Session session = sessionFactory.openSession();

        // Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close the Session
        session.close();

        // Return the Contact object
        return contact;
    }


    private static void update(Contact contact) {

        // Open Session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to Update the Contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the Session
        session.close();

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

    private static int save(Contact contact) {
        // Open Session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        // Add one more dependency to the gradle.build file
        // compile 'javax.transaction:jta:1.1'
        session.beginTransaction();

        // Use the Session to save the contact
        int id = (int) session.save(contact);

        // Commit the Transaction
        session.getTransaction().commit();

        // Close the Session
        session.close();

        return id;
    }
}
