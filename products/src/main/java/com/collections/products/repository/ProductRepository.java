package com.collections.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.collections.products.model.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> { }


