package com.collections.products.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.collections.products.model.ProductEntity;
import com.collections.products.service.ProductService;



@RestController
@RequestMapping(path="/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    

    @GetMapping(path="/{id}")
    public ResponseEntity<EntityModel<ProductEntity>> getProduct(@PathVariable Long id){
        ProductEntity product = productService.getProduct(id);
        EntityModel<ProductEntity> productModel = EntityModel.of(product, 
            linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel(),
            linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")
        );
        return ResponseEntity.ok(productModel);
    }

    @GetMapping(path="/products")
    public ResponseEntity<CollectionModel<EntityModel<ProductEntity>>> getAllProducts(){
        List<ProductEntity> products = productService.getProducts();
        List<EntityModel<ProductEntity>> productModels = products.stream()
            .map(product -> EntityModel.of(product, 
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
            .collect(Collectors.toList());
        CollectionModel<EntityModel<ProductEntity>> collectionModel = CollectionModel.of(productModels, 
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }
      

    @PostMapping
    public ResponseEntity<EntityModel<ProductEntity>> addProduct(@RequestBody ProductEntity product){
        ProductEntity newProduct = productService.createProduct(product);
        newProduct = addLinksToProduct(newProduct);
        EntityModel<ProductEntity> productModel = EntityModel.of(newProduct, 
            linkTo(methodOn(ProductController.class).getProduct(newProduct.getId())).withSelfRel(),
            linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")
        );
        
        return ResponseEntity
                .created(productModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productModel);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<EntityModel<ProductEntity>> updateProduct(@RequestBody ProductEntity product, @PathVariable Long id){
        ProductEntity updatedProduct = productService.updateProduct(id, product);
        updatedProduct = addLinksToProduct(updatedProduct);
        EntityModel<ProductEntity> productModel = EntityModel.of(updatedProduct,
            linkTo(methodOn(ProductController.class).getProduct(updatedProduct.getId())).withSelfRel(),
            linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        return ResponseEntity.ok(productModel);

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    public ProductEntity addLinksToProduct(ProductEntity product){
        Link selfLink = linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel();
        product.getLinks().add(selfLink);
        return product;
    }

}
