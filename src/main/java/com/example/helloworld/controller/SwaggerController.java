package com.example.helloworld.controller;

import com.example.helloworld.service.SalesforceAuthenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "User Management System", description = "Operations pertaining to user in User Management System")
public class SwaggerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SalesforceAuthenticator salesforceAuthService;

    // Salesforce base URL
    private static final String SALESFORCE_BASE_URL = "https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/";

    // Categories CRUD Endpoints

    @ApiOperation(value = "View the list of categories and their endpoints", tags = "Category Operations")
    @GetMapping("/categories2")
    public ResponseEntity<List<Map<String, Object>>> getCategories() {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> categoriesResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/categorie",
                    HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class
            );

            return ResponseEntity.ok(categoriesResponse.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return getCategories(); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Create a new category", tags = "Category Operations")
    @PostMapping("/categories2")
    public ResponseEntity<Object> createCategory(@RequestBody Map<String, Object> categoryPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(categoryPayload, headers);

            ResponseEntity<Object> createCategoryResponse = restTemplate.postForEntity(instanceUrl + "/services/apexrest/categorie", request, Object.class);

            return new ResponseEntity<>(createCategoryResponse.getBody(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return createCategory(categoryPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Update an existing category", tags = "Category Operations")
    @PutMapping("/categories2/{categoryId}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable String categoryId,
            @RequestBody Map<String, Object> categoryPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(categoryPayload, headers);

            ResponseEntity<Object> updateCategoryResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/categorie/" + categoryId,
                    HttpMethod.PUT,
                    request,
                    Object.class
            );

            return new ResponseEntity<>(updateCategoryResponse.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return updateCategory(categoryId, categoryPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Delete an existing category", tags = "Category Operations")
    @DeleteMapping("/categories2/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String categoryId) {
        try {
            salesforceAuthService.authenticate();
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> deleteCategoryResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/categorie/" + categoryId,
                    HttpMethod.DELETE,
                    entity,
                    Object.class
            );

            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate();
                return deleteCategory(categoryId);
            } else {
                throw e;
            }
        }
    }

    // User CRUD Endpoints
    @ApiOperation(value = "View the list of users and their endpoints", tags = "User Operations")
    @GetMapping("/users1")
    public ResponseEntity<List<Map<String, Object>>> getUsers(Model model) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();

            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> usersResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/users",
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class
            );

            return ResponseEntity.ok(usersResponse.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                // Handle token expiry
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return getUsers(model); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Create a new user", tags = "User Operations")
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody Map<String, Object> userPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

            ResponseEntity<Object> createUserResponse = restTemplate.postForEntity(instanceUrl + "/services/apexrest/users", request, Object.class);

            return new ResponseEntity<>(createUserResponse.getBody(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                // Handle token expiry
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return createUser(userPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Update an existing user", tags = "User Operations")
    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable String userId,
            @RequestBody Map<String, Object> userPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

            ResponseEntity<Object> updateUserResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/users/" + userId,
                    HttpMethod.PUT,
                    request,
                    Object.class
            );

            return new ResponseEntity<>(updateUserResponse.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                // Handle token expiry
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return updateUser(userId, userPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }


    @ApiOperation(value = "Delete an existing user", tags = "User Operations")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
        try {
            salesforceAuthService.authenticate();
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> deleteUserResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/users/" + userId,
                    HttpMethod.DELETE,
                    entity,
                    Object.class
            );

            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate();
                return deleteUser(userId);
            } else {
                throw e;
            }
        }
    }

    // Product CRUD Endpoints

    @ApiOperation(value = "View the list of products and their endpoints", tags = "Product Operations")
    @GetMapping("/products1")
    public ResponseEntity<List<Map<String, Object>>> getProducts() {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> productsResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/products",
                    HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class
            );

            return ResponseEntity.ok(productsResponse.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return getProducts(); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Create a new product", tags = "Product Operations")
    @PostMapping("/products")
    public ResponseEntity<Object> createProduct(@RequestBody Map<String, Object> productPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(productPayload, headers);

            ResponseEntity<Object> createProductResponse = restTemplate.postForEntity(instanceUrl + "/services/apexrest/products", request, Object.class);

            return new ResponseEntity<>(createProductResponse.getBody(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return createProduct(productPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Update an existing product", tags = "Product Operations")
    @PutMapping("/products/{productId}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable String productId,
            @RequestBody Map<String, Object> productPayload) {
        try {
            salesforceAuthService.authenticate(); // Ensure authentication
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(productPayload, headers);

            ResponseEntity<Object> updateProductResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/products/" + productId,
                    HttpMethod.PUT,
                    request,
                    Object.class
            );

            return new ResponseEntity<>(updateProductResponse.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate(); // Re-authenticate and get a new token
                return updateProduct(productId, productPayload); // Retry the request
            } else {
                throw e; // Rethrow other exceptions
            }
        }
    }

    @ApiOperation(value = "Delete an existing product", tags = "Product Operations")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String productId) {
        try {
            salesforceAuthService.authenticate();
            String instanceUrl = salesforceAuthService.getInstanceUrl();
            String sessionToken = salesforceAuthService.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + sessionToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> deleteProductResponse = restTemplate.exchange(
                    instanceUrl + "/services/apexrest/products/" + productId,
                    HttpMethod.DELETE,
                    entity,
                    Object.class
            );

            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                salesforceAuthService.authenticate();
                return deleteProduct(productId);
            } else {
                throw e;
            }
        }
    }

}
