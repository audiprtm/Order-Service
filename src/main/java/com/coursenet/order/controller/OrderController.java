package com.coursenet.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<OrderResponseDTO>createOrder(@RequestBody OrderRequestDTO orderRequest) {
		return orderService.createOrder(orderRequest);
	}
}
