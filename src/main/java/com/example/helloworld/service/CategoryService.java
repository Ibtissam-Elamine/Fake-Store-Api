package com.example.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final SalesforceDataService salesforceDataService;

    @Autowired
    public CategoryService(SalesforceDataService salesforceDataService) {
        this.salesforceDataService = salesforceDataService;
    }

    public Object getCategoryList() {
        return salesforceDataService.getSalesforceCategories();
    }

    public Object getCategoryById(String categoryId) {
        return salesforceDataService.getCategoryById(categoryId);
    }

    public Object createCategory(Object newCategoryData) {
        return salesforceDataService.createCategory(newCategoryData);
    }

    public Object updateCategory(String categoryId, Object updatedCategoryData) {
        return salesforceDataService.updateCategory(categoryId, updatedCategoryData);
    }

    public Object deleteCategory(String categoryId) {
        return salesforceDataService.deleteCategory(categoryId);
    }
}
