package com.example.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final SalesforceDataService salesforceDataService;

    @Autowired
    public ProductService(SalesforceDataService salesforceDataService) {
        this.salesforceDataService = salesforceDataService;
    }

    public Object getProductList() {
        return salesforceDataService.getSalesforceProducts();
    }

    public Object getProductById(String productId) {
        return salesforceDataService.getProductById(productId);
    }

    public Object getProductsByCategory(String categoryName) {
        return salesforceDataService.getProductsByCategory(categoryName);
    }

    // Nouvelle méthode pour récupérer un nombre fini de produits
    public Object getLimitedProducts(int limit) {
        return salesforceDataService.getLimitedProducts(limit);
    }

    public Object createProduct(Object newProductData) {
        return salesforceDataService.createProduct(newProductData);
    }

    public Object updateProduct(String productId, Object updatedProductData) {
        return salesforceDataService.updateProduct(productId, updatedProductData);
    }

    public Object deleteProduct(String productId) {
        return salesforceDataService.deleteProduct(productId);
    }
}
