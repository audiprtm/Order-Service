package com.coursenet.order.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coursenet.order.client.DeliveryServiceClient;
import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.DeliveryStatusRequestDTO;
import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.dto.OrderStatusRequestDTO;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.enums.DeliveryStatus;
import com.coursenet.order.enums.OrderStatus;
import com.coursenet.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		log.info("Create order Controller Finished, "
				+ "request: "+orderRequest.toString()
				+ ",response : "+orderResponseDTO.toString()
		);
		return new ResponseEntity<>(orderResponseDTO,HttpStatus.CREATED);
	}

	public ResponseEntity<OrderResponseDTO> updateStatusOrder(String token, OrderStatusRequestDTO orderStatusRequest) {
		Optional<Orders> order = orderRepository.findById(orderStatusRequest.getId());
		if (!order.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		OrderResponseDTO orderResponseDTO;
		// nge update status nya
		switch (orderStatusRequest.getStatus()) {
			case IN_PROCESS:
				orderResponseDTO = processOrder(token, order.get());
				break;
			case FINISHED:
				orderResponseDTO = finishOrder(order.get());
				break;
			case CANCELLED:
				orderResponseDTO = cancelOrder(token, order.get());
				break;
			default:
				orderResponseDTO = null;
				break;
		}

		if (orderResponseDTO == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Update Status Order Controller Finished, "
				+ "request: "+orderStatusRequest.toString()
				+ ",response : "+orderResponseDTO.toString()
		);
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

	private OrderResponseDTO cancelOrder(String token, Orders order) {
		if(order.getStatus()==OrderStatus.FINISHED || order.getStatus()==OrderStatus.CANCELLED) {
			return null;
		}
		
		//Kalo dia in process, cancel delivery ke delivery-service
		if(order.getStatus()==OrderStatus.IN_PROCESS) {
			try {
				deliveryServiceClient.updateStatusDelivery(token,
						DeliveryStatusRequestDTO
						.builder()
						.orderId(order.getId())
						.invoice(order.getInvoice())
						.shipperId(order.getShipperId())
						.status(DeliveryStatus.CANCELLED)
						.build()
						);
			} catch (Exception e) {
				log.error(e.toString());
				return null;
			}
		}
 		
		//Save ke database & Return Response
		order.setStatus(OrderStatus.CANCELLED);
		order= orderRepository.save(order);
		
		return new OrderResponseDTO(order);
	}

	private OrderResponseDTO processOrder(String token, Orders order) {
		//Kalo dia bukan created, return error
		if(order.getStatus()!=OrderStatus.CREATED) {
			return null;
		}

		//Kalo dia created, bikin delivery ke delivery-service
		try {
			deliveryServiceClient.createDelivery(
					token, DeliveryRequestDTO
					.builder()
					.invoice(order.getInvoice())
					.orderId(order.getId())
					.shipperId(order.getShipperId())
					.goodsName(order.getGoodsName())
					.build()
					);
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
 		
		//Save ke database & Return Response
		order.setStatus(OrderStatus.IN_PROCESS);
		order= orderRepository.save(order);
		
		return new OrderResponseDTO(order);
	}
}
