package com.coursenet.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.dto.OrderStatusRequestDTO;
import com.coursenet.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<OrderResponseDTO>createOrder(@RequestBody OrderRequestDTO orderRequest) {
		log.info("Create order Controller Started, request: "+orderRequest.toString());
		return orderService.createOrder(orderRequest);
	}
	
	@PostMapping("/orders/update-status")
	public ResponseEntity<OrderResponseDTO>updateStatusOrder(
			@RequestHeader("Authorization") String token,
			@RequestBody OrderStatusRequestDTO orderStatusRequest) {
		log.info("Update Status Order Controller Started, request: "+orderStatusRequest.toString());
		return orderService.updateStatusOrder(token, orderStatusRequest);
	}
	
}
