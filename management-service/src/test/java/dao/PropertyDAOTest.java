package dao;

import domain.Manager;
import domain.Property;
import domain.Tenant;
import org.jdbi.v3.core.Handle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PropertyDAOTest {

    private PropertyDAO propertyDAO;
    private Property property1;
    private Property property2;
    private Property property3;

    @BeforeAll
    public static void initialise() {
        JdbiDAOFactory.setJdbcUri("jdbc:postgresql://localhost:1244/tests");

        // Insert test data
        try (Handle handle = JdbiDAOFactory.getJdbi().open()) {
            handle.execute("DELETE FROM Tenant WHERE username = 'johndoe'");
            handle.execute("DELETE FROM Tenant WHERE username = 'janedoe'");
            handle.execute("DELETE FROM Tenant WHERE username = 'johnsmith'");
            handle.execute("DELETE FROM Manager WHERE username = 'janesmith'");

            handle.execute("INSERT INTO Tenant (firstName, lastName, phoneNumber, username, password) VALUES ('John', 'Doe', '123456789', 'johndoe', 'password')");
            handle.execute("INSERT INTO Tenant (firstName, lastName, phoneNumber, username, password) VALUES ('Jane', 'Doe', '987654321', 'janedoe', 'password')");
            handle.execute("INSERT INTO Tenant (firstName, lastName, phoneNumber, username, password) VALUES ('Bob', 'Smith', '123456789', 'johnsmith', 'password')");

            handle.execute("INSERT INTO Manager (firstName, lastName, phoneNumber, username, password) VALUES ('Jane', 'Smith', '987654321', 'janesmith', 'password')");
        }
    }


    @BeforeEach
    void setUp() {
//        propertyDAO = new PropertyCollectionsDAO();
        propertyDAO = JdbiDAOFactory.getPropertyDAO();

        Tenant tenant1 = new Tenant("John", "Doe", "123456789", "johndoe", "password");
        Tenant tenant2 = new Tenant("Jane", "Doe", "987654321", "janedoe", "password");
        Tenant tenant3 = new Tenant("Bob", "Smith", "123456789", "johnsmith", "password");

        Manager manager1 = new Manager("Jane", "Smith", "987654321", "janesmith", "password");

        property1 = new Property("Property 1", "Address 1", tenant1.getUsername(), manager1);
        property2 = new Property("Property 2", "Address 2", tenant2.getUsername(), manager1);
        property3 = new Property("Property 3", "Address 3", tenant3.getUsername(), manager1);

        propertyDAO.createProperty(property1);
        propertyDAO.createProperty(property2);

    }

    @AfterEach
    void tearDown() {
        propertyDAO.deleteProperty(property1);
        propertyDAO.deleteProperty(property2);
        propertyDAO.deleteProperty(property3);
    }

    @Test
    void createProperty() {
        //check that property3 does not already exist
        assertThat(propertyDAO.getAllProperties(), hasSize(2));
        assertThat(propertyDAO.getAllProperties(), not(hasItem(property3)));

        //create property3
        propertyDAO.createProperty(property3);

        //check propertyDAO contains Property3
        assertThat(propertyDAO.getAllProperties(), hasSize(3));
        assertThat(propertyDAO.getAllProperties(), hasItem(property3));

    }

    @Test
    void updateProperty() {
        //check that property1 has the correct name
        assertThat(propertyDAO.getAllProperties(), hasItem(property1));

        //change the name of property1
        property1.setName("New Property 1");
        propertyDAO.updateProperty(property1);

        //check that property1 has the new name
        assertThat(propertyDAO.getAllProperties(), hasItem(property1));
    }

    @Test
    void deleteProperty() {
        //check that property1 exists
        assertThat(propertyDAO.getAllProperties(), hasSize(2));
        assertThat(propertyDAO.getAllProperties(), hasItem(property1));

        //delete property1
        propertyDAO.deleteProperty(property1);

        //check that property1 has been deleted
        assertThat(propertyDAO.getAllProperties(), hasSize(1));
        assertThat(propertyDAO.getAllProperties(), not(hasItem(property1)));

    }

    @Test
    void getPropertyByAddress() {
        //check that property1 is in the DAO
        assertThat(propertyDAO.getPropertyByAddress("Address 1"), is(property1));
    }

    @Test
    void getAllProperties() {
        //check that all properties are in the DAO
        assertThat(propertyDAO.getAllProperties(), hasItem(property1));
        assertThat(propertyDAO.getAllProperties(), hasItem(property2));
        assertThat(propertyDAO.getAllProperties(), hasSize(2));
    }

    @Test
    void getPropertiesByManagerUsername() {
        //check that property1 and property2 are in the DAO
        assertThat(propertyDAO.getPropertiesByManagerUsername("janesmith"), hasItem(property1));
        assertThat(propertyDAO.getPropertiesByManagerUsername("janesmith"), hasItem(property2));
        assertThat(propertyDAO.getPropertiesByManagerUsername("janesmith"), hasSize(2));

    }
}