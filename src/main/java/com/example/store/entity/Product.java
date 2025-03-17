package com.example.store.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        // Avoid printing orders to prevent infinite recursion
        return "Product{id=" + id + ", name='" + description + "'}";
    }
}
