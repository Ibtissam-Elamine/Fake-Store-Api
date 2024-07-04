package com.example.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final SalesforceDataService salesforceDataService;

    @Autowired
    public UserService(SalesforceDataService salesforceDataService) {
        this.salesforceDataService = salesforceDataService;
    }

   /* public Object getListUsers(String act) {

        return salesforceDataService.getSalesforceUsers(act);
    }*/
    public Object getListUsers(String act) {
        return salesforceDataService.getData(
                "users",act);
    }

    public Object getUserById(String userId) {
        return salesforceDataService.getUserById(userId);
    }

    public Object createUser(Object newUserData) {
        return salesforceDataService.createUser(newUserData);
    }

    public Object updateUser(String userId,Object updatedUserData) {
        return salesforceDataService.updateUser(userId, updatedUserData);
    }

    public Object deleteUser(String userId) {
        return salesforceDataService.deleteUser(userId);
    }

    // Méthode pour obtenir les administrateurs
    public Object getAdmins() {
        return salesforceDataService.getSalesforceUsersByRole("Admin");
    }

    // Méthode pour obtenir les clients
    public Object getCustomers() {
        return salesforceDataService.getSalesforceUsersByRole("Customer");
    }

    // Nouvelle méthode pour obtenir les utilisateurs par rôle
    public Object getUsersByRole(String role) {
        return salesforceDataService.getSalesforceUsersByRole(role);
    }


    // Nouvelle méthode pour récupérer un nombre fini de user
    public Object getLimitedProducts(int limit) {
        return salesforceDataService.getLimitedUsers(limit);
    }
}
