package dao;

import domain.Property;

import java.util.Collection;

public interface PropertyDAO {

    void createProperty(Property property);

    void updateProperty(Property property);

    void deleteProperty(Property property);

    Property getPropertyByName(String name);

    Collection<Property> getAllProperties();

    Collection<Property> getPropertiesByManagerUsername(String username);

}
