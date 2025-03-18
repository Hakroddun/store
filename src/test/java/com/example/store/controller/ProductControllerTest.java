package com.example.store.controller;

import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.CustomerMapper;
import com.example.store.mapper.OrderMapper;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;
import com.example.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ComponentScan(basePackageClasses = {CustomerMapper.class, OrderMapper.class})
@RequiredArgsConstructor
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductService productService;

    private Product product;
    private String productDescription = "Test Product";
    private Order order;
    private String orderDescription = "Test Order";

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setDescription(orderDescription);
        order.setId(1L);

        product = new Product();
        product.setDescription(productDescription);
        product.setId(1L);
        product.setOrders(List.of(order));
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderRepository.findAllById(List.of(product.getId()))).thenReturn(List.of(order));
        when(productRepository.save(product)).thenReturn(product);
        when(productService.createProduct(product)).thenReturn(productMapper.productToProductDTO(product));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(productDescription))
                .andExpect(jsonPath("$.orders[0].description").value(orderDescription));
    }

    @Test
    void testGetOrders() throws Exception {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productService.getAllProducts()).thenReturn(productMapper.productsToProductDTOs(List.of(product)));

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(productDescription))
                .andExpect(jsonPath("$[0].orders[0].description").value(orderDescription));
    }

    @Test
    void testGetOrderById() throws Exception {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productService.getProductById(product.getId())).thenReturn(productMapper.productToProductDTO(product));

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(productDescription))
                .andExpect(jsonPath("$.orders[0].description").value(orderDescription));
    }
}
