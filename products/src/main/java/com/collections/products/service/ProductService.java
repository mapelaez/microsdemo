package com.collections.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.collections.products.error.ProductNotFoundException;
import com.collections.products.model.ProductEntity;
import com.collections.products.repository.ProductRepository;


@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getProducts(){
        Iterable<ProductEntity> iterable = productRepository.findAll();
        List<ProductEntity> productList = new ArrayList<>();
        iterable.forEach(productList::add);
        return productList;
    }

    public ProductEntity getProduct(Long id){
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductEntity createProduct(ProductEntity product){
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct){
        try{
            ProductEntity productToUpdate = getProduct(id);
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setDescription(updatedProduct.getDescription());
            productToUpdate.setType(updatedProduct.getType());
            return productRepository.save(productToUpdate);
        }
        catch(ProductNotFoundException ex){
            throw new ProductNotFoundException(id);
        }
    }

    public void deleteProduct(Long id){
        try{
            productRepository.deleteById(id);    
        }
        catch(ProductNotFoundException ex){
            throw new ProductNotFoundException(id);
        }
    }


}
