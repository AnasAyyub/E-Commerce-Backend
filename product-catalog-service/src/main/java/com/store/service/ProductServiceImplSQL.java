package com.store.service;

import com.store.model.Product;
import com.store.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ProductServiceImplSQL implements ProductService{
    private ProductRepository repository;

    public ProductServiceImplSQL(ProductRepository repository){
        this.repository=repository;
    }
    @Override
    public Product getProduct(long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        repository.save(product);
    }
}
