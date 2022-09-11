package com.example.elasticsearch_springboot.service;

import com.example.elasticsearch_springboot.documents.Product;
import com.example.elasticsearch_springboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductSearchService {

    private static final String PRODUCT_INDEX = "productindex";
    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public List<Product> fetchProductNamesContaining(final String name) {
        return productRepository.findByNameContaining(name);
    }


    public List<Product> processSearch(final String query) {

        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "name", "description").fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder).build();

        // 2. Execute search
        SearchHits<Product> productHits = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        // 3. Map searchHits to product list
        List<Product> productMatches = new ArrayList<Product>();
        productHits.forEach(srchHit -> {
            productMatches.add(srchHit.getContent());
        });
        return productMatches;
    }

    public List<String> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name", query + "*");

        Query searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder).withPageable(PageRequest.of(0, 5)).build();

        SearchHits<Product> searchSuggestions = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        List<String> suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit -> {
            suggestions.add(searchHit.getContent().getName());
        });
        return suggestions;
    }
}
