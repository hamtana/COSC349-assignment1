/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Manager;
import domain.Property;
import domain.Request;
import domain.Tenant;
import org.checkerframework.checker.interning.qual.EqualsMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author hamishp
 */
public class RequestCollectionsDAO implements RequestDAO {

    private static final HashMap<String, Request> requests = new HashMap<String, Request>();
    private static final Multimap<String, Request> requestsByTenant = HashMultimap.create();

    //Test data to check if it visible on the website.
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public RequestCollectionsDAO(){
        if(requests.isEmpty()){

            //Make four dummy tenants
            Tenant tenant = new Tenant("0001", "John", "Doe", "020321456", "john", "password");
            Tenant tenant2 = new Tenant("0002", "Jane", "Doe", "020321456", "jane", "password");

            //Make a manager
            Manager manager = new Manager("0001", "Steve", "Jobs", "020321456", "steve", "password");

            //Nake 2 properties
            Property property = new Property("0001", "The White House", "12 North Rd", tenant, manager);
            Property property2 = new Property("0002", "The White House", "12 North Rd", tenant2, manager);

            //Create some dummy data
            Request request1 = new Request("0001", "Broken Toilet", "The toilet is broken", true, property , tenant, false);
            Request request2 = new Request("0002", "Broken Window", "The window is broken", false,property , tenant, false);
            Request request3 = new Request("0003", "Broken Door", "The door is broken", true, property, tenant, false);
            Request request4 = new Request("0004", "Broken Roof", "The roof is broken", false, property2, tenant2, false);

            requests.put(request1.getId(), request1);
            requests.put(request2.getId(), request2);
            requests.put(request3.getId(), request3);
            requests.put(request4.getId(), request4);

            requestsByTenant.put(request1.getTenant().getId(), request1);
            requestsByTenant.put(request2.getTenant().getId(), request2);
            requestsByTenant.put(request3.getTenant().getId(), request3);
            requestsByTenant.put(request4.getTenant().getId(), request4);

        }

    }

    @Override
    public List<Request> getAllRequests(){
        return new ArrayList<Request>(requests.values());
    }

    @Override
    public Request getRequestById(String id){
        return requests.get(id);
    }

    @Override
    public void createRequest(Request request){
        requests.put(request.getId(), request);
        requestsByTenant.put(request.getTenant().getId(), request);
    }

   @Override
   public void updateRequest(Request request){
        requests.put(request.getId(), request);
        requestsByTenant.put(request.getTenant().getId(), request);
    }

    @Override
    public void deleteRequest(Request request) {
        requests.remove(request.getId());
        requestsByTenant.remove(request.getTenant().getId(), request);
    }



}