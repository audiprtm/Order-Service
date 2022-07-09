package com.coursenet.order.dto;

import java.time.LocalDateTime;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	private long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String goodsName;	
	private String invoice;
	private OrderStatus status;
	private int shipperId;
	
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
}
