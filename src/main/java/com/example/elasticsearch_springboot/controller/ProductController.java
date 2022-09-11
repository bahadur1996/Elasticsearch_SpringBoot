package com.example.elasticsearch_springboot.controller;

import com.example.elasticsearch_springboot.documents.Product;
import com.example.elasticsearch_springboot.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductSearchService searchService;

    @GetMapping("/search")
    public String home(Model model) {
        List<Product> products = searchService.fetchProductNamesContaining("Hornby");

        List<String> names = products.stream().map(Product::getName).collect(Collectors.toList());

        model.addAttribute("names", names);
        return "search";
    }
}
