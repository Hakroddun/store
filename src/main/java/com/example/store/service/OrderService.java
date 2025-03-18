package com.example.store.service;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.exception.CustomerNotFoundException;
import com.example.store.exception.ProductNotFoundException;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.CustomerRepository;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    public List<OrderDTO> getAllOrders() {
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.orderToOrderDTO(order);
    }

    public OrderDTO createOrder(Order order) {
        // Ensure the customer exists
        Customer customer = customerRepository
                .findById(order.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer id " + order.getCustomer().getId() + " not found"));

        order.setCustomer(customer);

        // Ensure products exist and set the products in the order
        List<Product> validProducts = order.getProducts().stream()
                .map(product -> {
                    if (product.getId() == null) {
                        throw new ProductNotFoundException("Product ID must be specified");
                    }
                    return productRepository
                            .findById(product.getId())
                            .orElseThrow(
                                    () -> new ProductNotFoundException("Product id " + product.getId() + " not found"));
                })
                .toList();

        order.setProducts(validProducts);

        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }
}
