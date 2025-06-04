package com.store.service;

import com.store.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

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

    public List<Product> getAllProducts(){
        Product[] products=template.getForObject(uri,Product[].class);
        List<Product> p= Arrays.asList(products);
        return p;
    }

    public void addProduct(Product product){
        template.postForObject(uri,product,Product.class);
    }

}
