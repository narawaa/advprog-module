package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentTest {
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "narawaa");

        this.paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithRequiredFields() {
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);

        assertEquals("payment-123", payment.getId());
        assertEquals(this.order, payment.getOrder());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING.getValue(), payment.getStatus());
    }

    @Test
    void testSetPaymentStatus() {
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherPaymentValidation_ValidVoucher() {
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherPaymentValidation_InvalidLength() {
        this.paymentData.put("voucherCode", "ESHOP123456");
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherPaymentValidation_InvalidPrefix() {
        this.paymentData.put("voucherCode", "SHOP12345678ABCD");
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherPaymentValidation_NotEnoughDigits() {
        this.paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("payment-123", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferPaymentValidation_Valid() {
        this.paymentData.put("bankName", "BCA");
        this.paymentData.put("referenceCode", "REF0707");
        Payment payment = new Payment("payment-123", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferPaymentValidationMissingBankName() {
        this.paymentData.put("referenceCode", "REF0707");
        Payment payment = new Payment("payment-123", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferPaymentValidationEmptyReferenceCode() {
        this.paymentData.put("bankName", "BCA");
        this.paymentData.put("referenceCode", "");
        Payment payment = new Payment("payment-123", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferPaymentValidationNullValues() {
        this.paymentData.put("bankName", null);
        this.paymentData.put("referenceCode", null);
        Payment payment = new Payment("payment-123", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testUnsupportedPaymentMethod() {
        Payment payment = new Payment("payment-123", this.order, "UNKNOWN_METHOD", this.paymentData);
        Exception exception = assertThrows(IllegalArgumentException.class, payment::validateAndSetStatus);

        assertTrue(exception.getMessage().contains("Unsupported payment method"));
    }
}