package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.model.ExceptionResponse;
import com.example.store.service.CustomerService;

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
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(
            tags = {"Customer"},
            summary = "Get all Customers",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = CustomerDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/search")
    @Operation(
            tags = {"Customer"},
            summary = "Search for a Customer",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Success",
                        content =
                                @Content(
                                        schema = @Schema(implementation = CustomerDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String query) {
        List<CustomerDTO> customerDTOs = customerService.searchCustomers(query);
        return ResponseEntity.ok(customerDTOs);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            tags = {"Customer"},
            summary = "Create a Customer",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created",
                        content =
                                @Content(
                                        schema = @Schema(implementation = CustomerDTO.class),
                                        mediaType = "application/json")),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        schema = @Schema(implementation = ExceptionResponse.class),
                                        mediaType = "application/json"))
            })
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        CustomerDTO createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }
}
