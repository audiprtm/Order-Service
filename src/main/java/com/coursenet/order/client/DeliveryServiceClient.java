package com.coursenet.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.DeliveryResponseDTO;
import com.coursenet.order.dto.DeliveryStatusRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeliveryServiceClient {
	@Value("${delivery.base.url}")
	private String deliveryBaseURL;
	
	@Value("${delivery.createDelivery.endpoint}")
	private String deliveryCreateDeliveryEndpoint;
	
	@Value("${delivery.updateStatusDelivery.endpoint}")
	private String deliveryUpdateStatusDeliveryEndpoint;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public void createDelivery(String token, DeliveryRequestDTO deliveryRequestDTO) throws Exception {
		log.info("DeliveryClient Create Delivery Started,"
					+"Request: "+deliveryRequestDTO.toString());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", token);
		HttpEntity<DeliveryRequestDTO> requestEntity = new HttpEntity<>(deliveryRequestDTO,httpHeaders);
		
		try {
			restTemplate.postForObject(
					String.format("%s%s", deliveryBaseURL,deliveryCreateDeliveryEndpoint), 
					requestEntity, 
					DeliveryResponseDTO.class);
		}catch(Exception e) {
			throw new Exception();
		}
		
	    log.info("DeliveryClient Create Delivery Finished, "
				+",Request: "+deliveryRequestDTO.toString());
	}

	public void updateStatusDelivery(String token, DeliveryStatusRequestDTO deliveryStatusRequestDTO) throws Exception {
		log.info("DeliveryClient Cancel Delivery Started,"
				+"Request: "+deliveryStatusRequestDTO.toString());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", token);
		HttpEntity<DeliveryStatusRequestDTO> requestEntity = new HttpEntity<>(deliveryStatusRequestDTO, httpHeaders);
		
		try {
			restTemplate.postForObject(
					String.format("%s%s", deliveryBaseURL, deliveryUpdateStatusDeliveryEndpoint),
					requestEntity, 
					DeliveryResponseDTO.class);
		}catch(Exception e) {
			throw new Exception();
		}
		
		log.info("DeliveryClient Cancel Delivery Finished,"
				+"Request: "+deliveryStatusRequestDTO.toString());
	}
}
