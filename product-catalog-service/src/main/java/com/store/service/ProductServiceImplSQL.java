package com.store.service;

import com.store.model.Category;
import com.store.model.Product;
import com.store.repositories.CategoryRepository;
import com.store.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ProductServiceImplSQL implements ProductService{
    private ProductRepository repository;
    private CategoryRepository categoryRepository;

    public ProductServiceImplSQL(ProductRepository repository,CategoryRepository categoryRepository){
        this.repository=repository;
        this.categoryRepository=categoryRepository;
    }
    @Override
    public Product getProduct(String id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public Product addProduct(Product product) {
        Category category=categoryRepository.findById(product.getCategoryId())
                .orElseThrow(()-> {
                    return new RuntimeException("Invalid Category");
                });

        return repository.save(product);
    }
}
