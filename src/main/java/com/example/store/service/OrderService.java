package com.example.store.service;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.CustomerRepository;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);

        // Ensure products exist and set the products in the order
        for (Product product : order.getProducts()) {
            productRepository.save(product); // Make sure products are persisted
        }

        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }
}
