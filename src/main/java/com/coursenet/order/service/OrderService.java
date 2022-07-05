package com.coursenet.order.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coursenet.order.client.DeliveryServiceClient;
import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.dto.OrderStatusRequestDTO;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.enums.OrderStatus;
import com.coursenet.order.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DeliveryServiceClient deliveryServiceClient;
	
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

	public ResponseEntity<OrderResponseDTO> updateStatusOrder(OrderStatusRequestDTO orderStatusRequest) {
		Optional<Orders> order = orderRepository.findById(orderStatusRequest.getId());
		if (!order.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		OrderResponseDTO orderResponseDTO;
		// nge update status nya
		switch (orderStatusRequest.getStatus()) {
		case IN_PROCESS:
			orderResponseDTO = processOrder(order.get());
			break;
		case FINISHED:
			orderResponseDTO = finishOrder(order.get());
			break;
		case CANCELLED:
			orderResponseDTO = cancelOrder(order.get());
			break;
		default:
			orderResponseDTO = null;
			break;
		}

		if (orderResponseDTO == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
	}

	private OrderResponseDTO finishOrder(Orders order) {
		// Kalo dia bukan in process, return error
		if (order.getStatus() != OrderStatus.IN_PROCESS) {
			return null;
		}

		// Save ke database & Return Response
		order.setStatus(OrderStatus.FINISHED);
		order = orderRepository.save(order);

		return new OrderResponseDTO(order);
	}

	private OrderResponseDTO cancelOrder(Orders order) {
		if(order.getStatus()!=OrderStatus.IN_PROCESS) {
			return null;
		}
		
		DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
		
		//Kalo dia in process, cancel delivery ke delivery-service
		deliveryServiceClient.cancelDelivery(deliveryRequestDTO);
 		
		//Save ke database & Return Response
		order.setStatus(OrderStatus.CANCELLED);
		order= orderRepository.save(order);
		
		return new OrderResponseDTO(order);
	}

	private OrderResponseDTO processOrder(Orders order) {
		//Kalo dia bukan created, return error
		if(order.getStatus()!=OrderStatus.CREATED) {
			return null;
		}
		
		DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
		
		//Kalo dia created, bikin delivery ke delivery-service
		deliveryServiceClient.createDelivery(deliveryRequestDTO);
 		
		//Save ke database & Return Response
		order.setStatus(OrderStatus.IN_PROCESS);
		order= orderRepository.save(order);
		
		return new OrderResponseDTO(order);
	}
}
