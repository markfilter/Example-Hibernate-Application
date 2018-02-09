package model;


import javax.persistence.*;

// @Entity Annotation is used to show that it should persist to
// the database. Hibernate will detect this annotation and will
// map a contact object to a single row in the database. There is
// an optional 'name' element for the annotation that will be
// used as the map's table name. The default, however, is the
// Class name and that is what will be used here.
@Entity
public class Contact {

    // Fields
    // @Column has an optional '@Column(name="columnName")', but by
    // default, the field name will be used.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private Long phone;

    // Default Constructor for JPA
    public Contact() { }

    //
    // Access Modifiers
    //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
