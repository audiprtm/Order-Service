package com.coursenet.order.dto;

import java.time.LocalDateTime;

import com.coursenet.order.entity.Orders;
import com.coursenet.order.enums.OrderStatus;

public class OrderResponseDTO {
	private long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String goodsName;	
	private String invoice;
	private OrderStatus status;
	private int shipperId;
	
	public OrderResponseDTO() {
		
	}
	
	public OrderResponseDTO(Orders order) {
		super();
		this.id = order.getId();
		this.createdAt = order.getCreatedAt();
		this.updatedAt = order.getUpdatedAt();
		this.goodsName = order.getGoodsName();
		this.status = order.getStatus();
		this.invoice = order.getInvoice();
		this.shipperId = order.getShipperId();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public int getShipperId() {
		return shipperId;
	}
	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}
}
