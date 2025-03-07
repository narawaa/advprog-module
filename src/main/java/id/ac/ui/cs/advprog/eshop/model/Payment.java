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

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "WAITING";
    }

    public void validateAndSetStatus() {
        if (method.equals("VOUCHER")) {
            validateVoucher();
        } else if (method.equals("BANK_TRANSFER")) {
            validateBankTransfer();
        } else {
            throw new IllegalArgumentException("Unsupported payment method");
        }
    }

    private void validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");
        setStatus("REJECTED");

        if (voucherCode == null || voucherCode.length() != 16) {
            return;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        if (digitCount != 8) {
            return;
        }

        setStatus("SUCCESS");
    }

    private void validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName != null && !bankName.isEmpty() && referenceCode != null && !referenceCode.isEmpty()) {
            setStatus("SUCCESS");
        } else {
            setStatus("REJECTED");
        }
    }

    public void setStatus(String status) {
        this.status = status;
        if (status.equals("SUCCESS")) {
            this.order.setStatus("SUCCESS");
        } else if (status.equals("REJECTED")) {
            this.order.setStatus("FAILED");
        }
    }
}