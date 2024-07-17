package com.korike.logistics.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.orders.OrderReqDto;
import org.springframework.security.authentication.BadCredentialsException;

public class CoreUtil {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat month_date = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	//	String actualDate = "2016-03-20";

		Date date = new Date();

		String month_name = month_date.format(date);
		System.out.println("Month :" +date.getDate()+"-"+ month_name);  //Mar 2016
	}

	
	public static OrderDetails getOrderDetails(String orderDetails) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		OrderReqDto orderReqDto = null;
		try {
			orderReqDto = objectMapper.readValue(orderDetails, OrderReqDto.class);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return orderReqDto.getoDetails();
	}

	public static String getAuthModeByNum(int numVal) {
		switch (numVal) {
			case 0:
				return "MO";
			case 1:
				return "MP";
			case 2:
				return "EO";
			case 3:
				return "EP";
			default:
				throw new BadCredentialsException("Invalid creds or auth mode");
		}
	}
}
