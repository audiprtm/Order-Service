package com.coursenet.order.dto;

import com.coursenet.order.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequestDTO {
	private long id;
	private OrderStatus status;
}
