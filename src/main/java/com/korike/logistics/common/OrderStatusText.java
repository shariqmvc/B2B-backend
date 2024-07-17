package com.korike.logistics.common;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusText {

    private static Map<String, String> map;

    static
    {
        map = new HashMap<>();
        map.put("CREATED", "Order received");
        map.put("ACCEPTED", "Order assigned");
        map.put("PREPARING", "Preparing order");
        map.put("GENERATING_BILL", "Generating final bill");
        map.put("GENERATED_BILL", "Generated final bill");
        map.put("AWAITING_PAYMENT", "Payment pending");
        map.put("PAYMENT_COMPLETED", "Payment completed");
        map.put("PAYMENT_FAILED", "Payment failed");
        map.put("PREPARING_DELIVERY", "Preparing for delivery");
        map.put("DELIVERY_INPROGRESS", "Delivery in progress");
        map.put("DELIVERY_ACKNOWLEDGING", "Confirming delivery");
        map.put("COMPLETED", "Order completed");
        map.put("FAILED", "Order failed");
        map.put("CANCELLED", "Order cancelled");
    }

    public static String getOrderStatusText(String orderStatus) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            if(orderStatus.equalsIgnoreCase(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }
}


