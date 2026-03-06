package com.store.service;

import com.store.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;

@Service

public class ProductServiceImpl implements ProductService{

    private RestTemplate template;
    private String uri="https://fakestoreapi.com/products/";

    @Autowired
    public ProductServiceImpl(RestTemplate template){
        this.template=template;
    }

    public Product getProduct(long id){
        Product response=template.getForObject(uri+id,Product.class);
        return response;
    }

    @Override
    public Product getProduct(String id) {
        return null;
    }

    public List<Product> getAllProducts(){
        Product[] products=template.getForObject(uri,Product[].class);
        List<Product> p= Arrays.asList(products);
        return p;
    }

    @Override
    public List<Product> getAllProductsByCategory(String categoryId) {
        return List.of();
    }

    public Product addProduct(Product product){
        template.postForObject(uri,product,Product.class);
        return null;
    }

}
