package com.example.helloworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class SalesforceDataService {

    private final SalesforceAuthenticator sfAuth;
    private final RestTemplate restTemplate;

    // Category Methods
    public Object getSalesforceCategories() {
        return getData("categorie", "");
    }

    public Object getCategoryById(String categoryId) {
        String url = "%s/services/apexrest/categorie/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), categoryId);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object createCategory(Object newCategoryData) {
        String url = "%s/services/apexrest/categorie";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.POST, newCategoryData);
    }

    public Object updateCategory(String categoryId, Object updatedCategoryData) {
        String url = "%s/services/apexrest/categorie/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), categoryId);
        return sendRequest(baseUrl, HttpMethod.PUT, updatedCategoryData);
    }

    public Object deleteCategory(String categoryId) {
        String url = "%s/services/apexrest/categorie/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), categoryId);
        return sendRequest(baseUrl, HttpMethod.DELETE, null);
    }

    // User Methods
    public Object getSalesforceUsers(String act) {
        return getData("users",act);
    }

    public Object getUserById(String userId) {
        String url = "%s/services/apexrest/users/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), userId);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object createUser(Object newUserData) {
        String url = "%s/services/apexrest/users";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.POST, newUserData);
    }

    public Object updateUser(String userId, Object updatedUserData) {
        String url = "%s/services/apexrest/users/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), userId);
        return sendRequest(baseUrl, HttpMethod.PUT, updatedUserData);
    }

    public Object deleteUser(String userId) {
        String url = "%s/services/apexrest/users/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), userId);
        return sendRequest(baseUrl, HttpMethod.DELETE, null);
    }
    // Méthode pour obtenir les utilisateurs par rôle
    public Object getSalesforceUsersByRole(String role) {
        String url = "%s/services/apexrest/users?role=%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), role);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    // Basket Methods
    public Object getSalesforceBasket() {
        String url = "%s/services/apexrest/basket";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object addToBasket(Object basketItemData) {
        String url = "%s/services/apexrest/basket";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.POST, basketItemData);
    }

    public Object removeFromBasket(String productId) {
        String url = "%s/services/apexrest/basket/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), productId);
        return sendRequest(baseUrl, HttpMethod.DELETE, null);
    }

    public Object clearBasket() {
        String url = "%s/services/apexrest/basket/clear";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.POST, null);
    }

    // Product Methods
    public Object getSalesforceProducts() {
        String url = "%s/services/apexrest/products";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }
    public Object getSalesforceProducts1(String act) {
        return getData("products", act);
    }
    public Object getSalesforceCategories1(String act) {
        return getData("categorie", act);
    }
    public Object getProductById(String productId) {
        String url = "%s/services/apexrest/products/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), productId);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object getProductsByCategory(String categoryName) {
        String url = "%s/services/apexrest/products?categoryName=%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), categoryName);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object createProduct(Object newProductData) {
        String url = "%s/services/apexrest/products";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());
        return sendRequest(baseUrl, HttpMethod.POST, newProductData);
    }

    public Object updateProduct(String productId, Object updatedProductData) {
        String url = "%s/services/apexrest/products/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), productId);
        return sendRequest(baseUrl, HttpMethod.PUT, updatedProductData);
    }

    public Object deleteProduct(String productId) {
        String url = "%s/services/apexrest/products/%s";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), productId);
        return sendRequest(baseUrl, HttpMethod.DELETE, null);
    }

    public Object getLimitedProducts(int limit) {
        String url = "%s/services/apexrest/products?limit=%d";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), limit);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }

    public Object getData(String endpoint, String act) {
        String url = "%s/services/apexrest/" + endpoint + "/" + act;
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add(AUTHORIZATION, String.format("Bearer %s", sfAuth.getAccessToken()));

        HttpEntity<?> request = new HttpEntity<>(headers);
        return restTemplate.exchange(baseUrl, HttpMethod.GET, request, Object.class);
    }

    // Private Method to Send Requests
    private Object sendRequest(String url, HttpMethod method, Object requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add(AUTHORIZATION, String.format("Bearer %s", sfAuth.getAccessToken()));

        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, method, request, Object.class);
    }

    public Object getLimitedUsers(int limit) {
        String url = "%s/services/apexrest/users?limit=%d";
        final String baseUrl = url.formatted(sfAuth.getInstanceUrl(), limit);
        return sendRequest(baseUrl, HttpMethod.GET, null);
    }
}
