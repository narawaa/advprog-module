package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order mockOrder;
    private Payment mockPayment;
    private Map<String, String> voucherCodePayment;
    private Map<String, String> bankPayment;

    @BeforeEach
    void setUp() {
        mockOrder = new Order("13652556-012a-4c07-b546-54eb1396d79b", getProducts(), 1708560000L, "narawaa", OrderStatus.WAITING_PAYMENT.getValue());

        voucherCodePayment = new HashMap<>();
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC5678");

        bankPayment = new HashMap<>();
        bankPayment.put("bankName", "BCA");
        bankPayment.put("referenceCode", "REF0707");

        mockPayment = new Payment("VOUCHER_CODE", mockOrder, "VOUCHER", voucherCodePayment);
    }

    private static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sabun");
        product2.setProductQuantity(1);
        products.add(product1);
        products.add(product2);
        return products;
    }

    @Test
    void testAddPaymentVoucherCode() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);
        Payment payment = paymentService.addPayment(mockOrder, "VOUCHER", voucherCodePayment);

        assertNotNull(payment);
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentBank() {
        Payment bankMockPayment = new Payment("BANK", mockOrder, "BANK_TRANSFER", bankPayment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(bankMockPayment);

        Payment payment = paymentService.addPayment(mockOrder, "BANK_TRANSFER", bankPayment);

        assertNotNull(payment);
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("REF0707", payment.getPaymentData().get("referenceCode"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatus() {
        mockPayment.setStatus(PaymentStatus.SUCCESS.getValue());
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment updatedPayment = paymentService.setStatus(mockPayment, PaymentStatus.SUCCESS.getValue());

        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        when(paymentRepository.findById(anyString())).thenReturn(mockPayment);

        Payment retrievedPayment = paymentService.getPayment("VOUCHER_CODE");

        assertNotNull(retrievedPayment);
        assertEquals(mockPayment, retrievedPayment);
        verify(paymentRepository, times(1)).findById(anyString());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> paymentList = Arrays.asList(mockPayment, mockPayment);
        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> retrievedPayments = paymentService.getAllPayments();

        assertNotNull(retrievedPayments);
        assertEquals(2, retrievedPayments.size());
        verify(paymentRepository, times(1)).findAll();
    }
}