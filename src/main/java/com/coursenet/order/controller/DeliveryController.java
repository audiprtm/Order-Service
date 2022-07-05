package com.coursenet.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.DeliveryResponseDTO;

@RestController
public class DeliveryController {
	
	@PostMapping("/deliveries")
	public ResponseEntity<DeliveryResponseDTO>createDelivery(@RequestBody DeliveryRequestDTO orderRequest) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
