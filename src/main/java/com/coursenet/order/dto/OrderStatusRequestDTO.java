package com.coursenet.order.dto;

import com.coursenet.order.enums.OrderStatus;

public class OrderStatusRequestDTO {
	private long id;
	private OrderStatus status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
