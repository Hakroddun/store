package com.example.store.controller;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.model.ExceptionResponse;
import com.example.store.service.OrderService;

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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(
            tags = {"Order"},
            summary = "Get all Orders",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = OrderDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOs = orderService.getAllOrders();
        return ResponseEntity.ok(orderDTOs);
    }

    // Endpoint to get an order by ID
    @GetMapping("/{id}")
    @Operation(
            tags = {"Order"},
            summary = "Get specified Order",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = OrderDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDTO);
    }

    // Endpoint to create an order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            tags = {"Order"},
            summary = "Create an order",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created",
                        content =
                                @Content(
                                        schema = @Schema(implementation = OrderDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        OrderDTO createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}
