package com.example.store.controller;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        System.out.println(orderRepository.findAll());
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderMapper.orderToOrderDTO(orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody Order order) {
        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }
}
