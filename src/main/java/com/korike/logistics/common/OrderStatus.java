package com.korike.logistics.common;

public enum OrderStatus {
	CREATED, ACCEPTED, PREPARING, PREPARED, GENERATING_BILL, GENERATED_BILL, AWAITING_PAYMENT, PAYMENT_COMPLETED,PAYMENT_FAILED, PREPARING_DELIVERY, DELIVERY_INPROGRESS, DELIVERY_ACKNOWLEDGING, COMPLETED, FAILED, CANCELLED
}
