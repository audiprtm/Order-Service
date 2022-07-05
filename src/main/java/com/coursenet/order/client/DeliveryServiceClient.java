package com.coursenet.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.DeliveryResponseDTO;

@Component
public class DeliveryServiceClient {
	@Value("${delivery.base.url}")
	private String deliveryBaseURL;
	
	@Value("${delivery.createDelivery.endpoint}")
	private String deliveryCreateDeliveryEndpoint;
	
	@Value("${delivery.updateStatusDelivery.endpoint}")
	private String deliveryUpdateStatusDeliveryEndpoint;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public void createDelivery(DeliveryRequestDTO deliveryRequestDTO) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<DeliveryRequestDTO> requestEntity = new HttpEntity<>(deliveryRequestDTO,httpHeaders);
		
		restTemplate.postForObject(
				String.format("%s%s", deliveryBaseURL,deliveryCreateDeliveryEndpoint), 
				requestEntity, 
				DeliveryResponseDTO.class);
	}

	public void cancelDelivery(DeliveryRequestDTO deliveryRequestDTO) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<DeliveryRequestDTO> requestEntity = new HttpEntity<>(deliveryRequestDTO,httpHeaders);
		
		restTemplate.postForObject(
				String.format("%s%s", deliveryBaseURL,deliveryUpdateStatusDeliveryEndpoint), 
				requestEntity, 
				DeliveryResponseDTO.class);
		
	}
}
