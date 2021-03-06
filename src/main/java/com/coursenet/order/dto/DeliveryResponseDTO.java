package com.coursenet.order.dto;

import java.time.LocalDateTime;

import com.coursenet.order.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {
	private long id;
	private long orderId;
	private String invoice;
	private int shipperId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private DeliveryStatus status;
}
