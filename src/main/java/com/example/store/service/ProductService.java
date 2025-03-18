package com.example.store.service;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        return productMapper.productsToProductDTOs(productRepository.findAll());
    }

    public ProductDTO getProductById(Long id) {
        return productMapper.productToProductDTO(productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id)));
    }

    public ProductDTO createProduct(Product product) {
        return productMapper.productToProductDTO(productRepository.save(product));
    }
}
