package com.example.elasticsearch_springboot;

import com.example.elasticsearch_springboot.documents.Product;
import com.example.elasticsearch_springboot.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ElasticsearchSpringBootApplicationTests {

    @Autowired
    ProductService productService;
    @Test
    void createProductTest() {
        Product product = new Product().setCategory("Car").setDescription("Description").setManufacturer("VOXY")
                .setName("NOAH").setPrice(100.00).setQuantity(3);

        productService.createProductIndex(product);
    }
    @Test
    void searchProductTest(){
        List<Product> productList = productService.searchByName("NOAH");

        for(Product product1: productList){
            System.out.printf("Got it");
        }
    }

}
