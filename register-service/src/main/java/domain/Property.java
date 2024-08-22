/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Collection;

/**
 *
 * @author hamishp
 */
public class Property {
    
    private String id;
    private String name;
    private Manager manager;
    private Collection<Tenant> tenants;

    public Property(String id, String name, Manager manager, Collection<Tenant> tenants) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.tenants = tenants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Collection<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Tenant> tenants) {
        this.tenants = tenants;
    }
    
    
    
}