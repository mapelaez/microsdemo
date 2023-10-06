package com.collections.products.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    
    private String description;

    private String type; 

    @Transient
    private List<Link> links = new ArrayList<>();


    //@Lob
    //private byte[] image;

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, String type){
        this.name = name;
        this.type = type;
        this.description = description;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Link> getLinks() {
        return links;
    }


    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
