
package com.example.helloworld.controller;

import com.example.helloworld.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@org.springframework.stereotype.Controller
public class KimController {
    private int id;

    @Autowired
    private ProductService productService;

    @Autowired
    private SalesforceDataService salesforceDataService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;



    @GetMapping("/home")
    public String showCategories(Model model) {
        Object categories = salesforceDataService.getSalesforceCategories1(""); // Vous devrez remplacer "act" par la valeur appropriée
        model.addAttribute("categories", categories);
        Object admins = userService.getListUsers("users?role=Admin");
        Object customers = userService.getListUsers("users?role=Customer");
        Object products = salesforceDataService.getSalesforceProducts1("products");
        model.addAttribute("admins", admins);
        System.out.println("admins" + admins);
        model.addAttribute("customers", customers);
        System.out.println("products" + products);
        model.addAttribute("products", products);
        return "home"; // Cela renvoie la vue Thymeleaf "home.html"
        //return "products";
    }



    @GetMapping("/documentation")
    public String showDocumentation() {
        // Logique de redirection vers la page de documentation
        return "documentation"; // Nom de la vue Thymeleaf pour la page de documentation
    }
    @GetMapping("/users")
    public String redirectUsers(Model model) {
        // Endpoint et description généraux
        model.addAttribute("endpoint1", "/users");
        model.addAttribute("description1", "You can access the list of users by using the /users endpoint.");

        // Récupération des données pour la première requête
        model.addAttribute("request1", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users");
        model.addAttribute("response1", userService.getListUsers(""));

        // Endpoint et description spécifiques à getUserById
        model.addAttribute("endpoint2", "/users/{userId}");
        model.addAttribute("description2", "Get a user by ID");

        // Récupération des données pour la deuxième requête
        String userId1 = "a0BQy000008eCSaMAM"; // Exemple d'ID de utilisateur
        model.addAttribute("request2", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users/" + userId1);
        model.addAttribute("response2", userService.getUserById(userId1));

        // Endpoint et description spécifiques à getProductByCategory
        model.addAttribute("endpoint6", "/products?categoryName={categoryName}");
        model.addAttribute("description6", "Get products by category name");

        // Exemple de requête pour getUsersbyrole
        String role = "Customer"; // Nom de la catégorie
        model.addAttribute("request6", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users?role=" + role);
        model.addAttribute("response6", userService.getUsersByRole(role));
        // Endpoint et description spécifiques à createUser
        model.addAttribute("endpoint3", "/users");
        model.addAttribute("description3", "Create a new user");

        // Exemple de requête pour createUser
        model.addAttribute("request3", "[POST] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users");
        model.addAttribute("requestBody3", "{\n"
                + "    \"userId\": \"a0BQy000008eCSVMA2\",\n"
                + "    \"role\": \"Customer\",\n"
                + "    \"lastName\": \"Khalid\",\n"
                + "    \"firstName\": \"Ahmed\",\n"
                + "    \"email\": \"ahmedkhalid@example.com\",\n"
                + "    \"birthdate\": \"1995-05-09\",\n"
                + "    \"avatar\": \"https://api.escuelajs.co/api/v1/files/10201.jpg\"\n"
                + "}");

        Object createUserResponseObject = userService.createUser(
                "{\n" +
                        "    \"role\": \"Customer\",\n" +
                        "    \"lastName\": \"Khalid\",\n" +
                        "    \"firstName\": \"Ahmed\",\n" +
                        "    \"email\": \"ahmedkhalid@example.com\",\n" +
                        "    \"birthdate\": \"1995-05-09\",\n" +
                        "    \"avatar\": \"https://api.escuelajs.co/api/v1/files/10201.jpg\"\n" +
                        "}");

        String createUserResponse = createUserResponseObject.toString();

        model.addAttribute("response3", createUserResponse);

        // Endpoint et description spécifiques à updateUser
        model.addAttribute("endpoint4", "/users/{userId}");
        model.addAttribute("description4", "Update an existing user");

        // Exemple de requête pour updateUser
        String userId2 = "a0BQy000008eCSaMAM";
        model.addAttribute("request4", "[PUT] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users/" + userId2);
        model.addAttribute("requestBody4", "{\n"+
                "    \"role\": \"Customer\",\n" +
                        "    \"lastName\": \"Khalid12\",\n" +
                        "    \"firstName\": \"Ahmed\",\n" +
                        "    \"email\": \"ahmedkhalid@example.com\",\n" +
                        "    \"birthdate\": \"1995-05-09\",\n" +
                        "    \"avatar\": \"https://api.escuelajs.co/api/v1/files/10201.jpg\"\n" +
                        "}");
        // Appel à votre service de mise à jour de produit
        Object updateUserResponseObject = userService.updateUser(userId2,
                "{\"userId\": \"a0BQy000008eCSaMAM\"," +
                        "    \"role\": \"Customer\",\n" +
                        "    \"lastName\": \"Young12\",\n" +
                        "    \"firstName\": \"Ahmed\",\n" +
                        "    \"email\": \"donnayoung@example.com\",\n" +
                        "    \"birthdate\": \"1995-05-09\",\n" +
                        "    \"avatar\": \"https://api.escuelajs.co/api/v1/files/10201.jpg\"\n" +
                        "}");

        String updateUserResponse = updateUserResponseObject.toString();

        model.addAttribute("response4", updateUserResponse);

        // Endpoint et description spécifiques à deleteUser
        model.addAttribute("endpoint5", "/users/{userId}");
        model.addAttribute("description5", "Delete an existing user");
// Exemple de requête pour deleteUser
          String userId3 = "a0BQy00000AoIQbMAN";
         model.addAttribute("request5", "[DELETE] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/users/" + userId3);
          model.addAttribute("response5", userService.deleteUser(userId3));


         return "users";
    }

    @GetMapping("/products")
    public String redirectProducts(Model model)  {
        // Endpoint et description généraux
        model.addAttribute("endpoint1", "/products");
        model.addAttribute("description1", "You can access the list of products by using the /products endpoint.");

        // Récupération des données pour la première requête
        model.addAttribute("request1", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products");
        model.addAttribute("response1", productService.getProductList());

        // Endpoint et description spécifiques à getProductById
        model.addAttribute("endpoint2", "/products/{productId}");
        model.addAttribute("description2", "Get a product by ID");

        // Récupération des données pour la deuxième requête
        // Remplacez "productId" par un ID de produit réel pour obtenir une réponse spécifique
        String productId1 = "a0DQy000006i5lRMAQ"; // Exemple d'ID de produit
        model.addAttribute("request2", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products/" + productId1);

        model.addAttribute("response2", productService.getProductById(productId1));

        // Endpoint et description spécifiques à getProductByCategory
        model.addAttribute("endpoint3", "/products?categoryName={categoryName}");
        model.addAttribute("description3", "Get products by category name");

        // Exemple de requête pour getProductByCategory
        String categoryName = "Clothes"; // Nom de la catégorie
        model.addAttribute("request3", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products?categoryName=" + categoryName);
        model.addAttribute("response3", productService.getProductsByCategory(categoryName));

        // Endpoint et description spécifiques à createProduct
        model.addAttribute("endpoint4", "/products");
        model.addAttribute("description4", "Create a new product");

        // Exemple de requête pour createProduct
        model.addAttribute("request4", "[POST] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products");
        model.addAttribute("requestBody4", "{\n"
                + "    \"title\": \"Product Title1\",\n"
                + "    \"description\": \"product description1\",\n"
                + "    \"price\": 94.99,\n"
                + "    \"images\": \"https://example.com/image.jpg\",\n"
                + "    \"categorie\": \"a0CQy00000AwRLV\"\n"
                + "}");

        // Endpoint et description spécifiques à updateProduct
        model.addAttribute("endpoint5", "/products/{productId}");
        model.addAttribute("description5", "Update an existing product");

        // Exemple de requête pour updateProduct
        String productId2 = "a0DQy000007tgU1";
        model.addAttribute("request5", "[PUT] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products/" + productId2);
        model.addAttribute("requestBody5", "{\n"
                + "    \"productId\": \"a0DQy000007tgU1\",\n"
                + "    \"title\": \"Updated Product Title\",\n"
                + "    \"description\": \"Updated product description\",\n"
                + "    \"price\": 99.99,\n"
                + "    \"imagesUrl\": \"https://example.com/updated-image.jpg\",\n"
                + "    \"categoryId\": \"a0CQy00000AwRLS\"\n"
                + "}");

        // Appel à votre service de création de produit
        Object createProductResponseObject = productService.createProduct(
                "{\"title\": \"Product Title1\"," +
                        "\"description\": \"Product description\"," +
                        "\"price\": 94.99," +
                        "\"images\": \"https://example.com/updated-image.jpg\"," +
                        "\"categorie\": \"a0CQy00000AwRLV\"}");

        String createProductResponse = createProductResponseObject.toString();
        model.addAttribute("response4", createProductResponse);

        // Appel à votre service de mise à jour de produit
        Object updateProductResponseObject = productService.updateProduct(productId2, "{\"productId\": \"a0DQy000007tgU1\",\"title\": \"Updated Product Title\",\"description\": \"Updated product description\",\"price\": 99.99,\"imagesUrl\": \"https://example.com/updated-image.jpg\",\"categoryId\": \"a0CQy00000AwRLS\"}");

        String updateProductResponse = updateProductResponseObject.toString();

        model.addAttribute("response5", updateProductResponse);

        // Endpoint et description spécifiques à getLimitedProducts
        model.addAttribute("endpoint6", "/limited-products");
        model.addAttribute("description6", "Get a limited number of products");

        // Récupération des données pour la sixième requête
        int limit = 5; // Exemple de limite de produits
        model.addAttribute("request6", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products?limit=" + limit);
        model.addAttribute("response6", productService.getLimitedProducts(limit));

        // Endpoint et description spécifiques à deleteProduct
        model.addAttribute("endpoint7", "/products/{productId}");
        model.addAttribute("description7", "Delete an existing product");

        // Exemple de requête pour deleteProduct
        String productId3 = "a0DQy000007KkYvMAK";
        model.addAttribute("request7", "[DELETE] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/products/" + productId3);
        model.addAttribute("response7", productService.deleteProduct(productId3));



        return "products";
    }
    @GetMapping("/category")
    public String redirectCategories(Model model) {
        // Endpoint et description généraux
        model.addAttribute("endpoint1", "/categorie");
        model.addAttribute("description1", "You can access the list of categories by using the /categories endpoint.");

        // Récupération des données pour la première requête
        model.addAttribute("request1", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/categorie");
        model.addAttribute("response1", categoryService.getCategoryList());

        // Endpoint et description spécifiques à getCategoryById
        model.addAttribute("endpoint2", "/categorie/{categoryId}");
        model.addAttribute("description2", "Get a category by ID");

        // Récupération des données pour la deuxième requête
        String categoryId1 = "a0CQy00000AwRLRMA3"; // Exemple d'ID de catégorie
        model.addAttribute("request2", "[GET] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/categorie/" + categoryId1);
        model.addAttribute("response2", categoryService.getCategoryById(categoryId1));

        // Endpoint et description spécifiques à createCategory
        model.addAttribute("endpoint3", "/categorie");
        model.addAttribute("description3", "Create a new category");

        // Exemple de requête pour createCategory
        model.addAttribute("request3", "[POST] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/categorie");
        model.addAttribute("requestBody3", "{\n"
                + "    \"name\": \"Category Name\",\n"
                + "    \"description\": \"Category description\"\n"
                + "}");
        model.addAttribute("response3", categoryService.createCategory(
                "{\n"
                        + "    \"name\": \"Category Name\",\n"
                        + "    \"image\": \"https://i.imgur.com/QkIa5tT.jpeg\"\n"
                        + "}"
        ));

        // Endpoint et description spécifiques à updateCategory
        model.addAttribute("endpoint4", "/categorie/{categoryId}");
        model.addAttribute("description4", "Update an existing category");

        // Exemple de requête pour updateCategory
        String categoryId2 = "a0CQy00000EdeVNMAZ";
        model.addAttribute("request4", "[PUT] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/categorie/" + categoryId2);
        model.addAttribute("requestBody4", "{\n"
                + "    \"name\": \"Cat2\",\n"
                + "    \"description\": \"Updated category description\"\n"
                + "}");
        model.addAttribute("response4", categoryService.createCategory(
                "{\n"
                        + "    \"name\": \"Cat2\",\n"
                        + "    \"image\": \"https://i.imgur.com/QkIa5tT.jpeg\"\n"
                        + "}"
        ));
        // Endpoint et description spécifiques à deleteCategory
        model.addAttribute("endpoint5", "/categories/{categoryId}");
        model.addAttribute("description5", "Delete an existing category");

        // Exemple de requête pour deleteCategory
        String categoryId3 = "a0CQy00000EgcUNMAZ";
        model.addAttribute("request5", "[DELETE] https://resourceful-panda-f3uzxi-dev-ed.trailblaze.my.salesforce.com/services/apexrest/categorie/" + categoryId3);
        model.addAttribute("response5", categoryService.deleteCategory(categoryId3));
        return "category";
    }

}







