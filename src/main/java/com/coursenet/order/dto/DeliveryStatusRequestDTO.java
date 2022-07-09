package com.coursenet.order.dto;

import com.coursenet.order.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusRequestDTO {
	private long orderId;
	private String invoice;
	private int shipperId;
	private DeliveryStatus status;
}
