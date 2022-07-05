package com.coursenet.order.enums;

public enum OrderStatus {
	CREATED,
	IN_PROCESS, //Dia akan menghit delivery service, untuk dikirim
	FINISHED, //Dia yang di hit oleh delivery service
	CANCELLED //Dia menghit delivery service, untuk cancel kiriman
}
