package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String paymentId, String paymentMethod, String paymentStatus, Map<String, String> paymentData, Order paymentOrder) {
        return;
    }

    public Payment(String paymentMethod, Map<String, String> paymentData, Order paymentOrder) {
        return;
    }
}