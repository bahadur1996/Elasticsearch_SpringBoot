package com.example.elasticsearch_springboot.service;

import com.example.elasticsearch_springboot.documents.Product;
import com.example.elasticsearch_springboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void createProductIndexBulk(final List<Product> products) {
        productRepository.saveAll(products);
    }

    public void createProductIndex(final Product product) {
        productRepository.save(product);
    }
}
