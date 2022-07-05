package com.coursenet.order.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.enums.OrderStatus;
import com.coursenet.order.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public ResponseEntity<OrderResponseDTO> createOrder(OrderRequestDTO orderRequest) {
		//Untuk bikin nomer invoice
		DateFormat dateFormat = new SimpleDateFormat("yyyymmddhmmss");
		String stringDate = dateFormat.format(new Date());
		
		//Save ke db
		Orders order = new Orders();
		order.setGoodsName(orderRequest.getGoodsName());
		order.setInvoice("INV/"+stringDate);
		order.setStatus(OrderStatus.CREATED);
		order.setShipperId(orderRequest.getShipperId());
		
		
		//Return response
		order = orderRepository.save(order);
		
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
		return new ResponseEntity<>(orderResponseDTO,HttpStatus.CREATED);
	}

}
