package com.duoc.OrderService.Client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.duoc.OrderService.Model.Product;

@Service
public class ProductClient {

    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProductById(Long id) {
        return restTemplate.getForObject("http://localhost:8081/api/productos/" + id, Product.class);
    }

    public List<Product> getAllProducts() {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8081/api/productos", Product[].class));
    }

    public Product updateProduct(Long id, Product product) {
        restTemplate.put("http://localhost:8081/api/productos/" + id, product);
        return product;
    }

}
