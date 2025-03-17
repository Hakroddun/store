package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.dto.ProductOrderDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    ProductOrderDTO productToProductOrderDTO(Order order);

    List<ProductOrderDTO> productToProductOrderDTOs(List<Order> orders);
}
