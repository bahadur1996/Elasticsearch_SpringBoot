package com.example.elasticsearch_springboot.controller;

import com.example.elasticsearch_springboot.documents.Product;
import com.example.elasticsearch_springboot.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ProductRestController {


    private final ProductSearchService searchService;


    @GetMapping("/products")
    @ResponseBody
    public List<Product> fetchByNameOrDesc(@RequestParam(value = "q", required = false) String query) {

        return searchService.processSearch(query);
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {

        return searchService.fetchSuggestions(query);
    }
}
