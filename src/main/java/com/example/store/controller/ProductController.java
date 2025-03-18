package com.example.store.controller;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.model.ExceptionResponse;
import com.example.store.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET: Return all products with their associated order IDs
    @GetMapping
    @Operation(
            tags = {"Product"},
            summary = "Get all Products",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ProductDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return ResponseEntity.ok(productDTOs);
    }

    // GET: Return a specific product by ID with its associated order IDs
    @GetMapping("/{id}")
    @Operation(
            tags = {"Product"},
            summary = "Get a specific Product",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ProductDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            tags = {"Product"},
            summary = "Create a Product",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ProductDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        ProductDTO createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}
