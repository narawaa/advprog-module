package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // This method is intentionally left empty as no setup is required before each test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateAndEdit() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(product1.getProductId());
        product2.setProductName("Sampo Cap Bambang Siapa?");
        product2.setProductQuantity(10);
        Product editedProduct = productRepository.edit(product2);

        assertNotNull(editedProduct);
        assertEquals("Sampo Cap Bambang Siapa?", editedProduct.getProductName());
        assertEquals(10, editedProduct.getProductQuantity());
    }

    @Test
    void testCreateAndDelete() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        productRepository.delete(product1);

        Iterator<Product> afterDelete = productRepository.findAll();
        assertFalse(afterDelete.hasNext());
    }

    @Test
    void testEditNonExistentProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Ini Apa?");
        product.setProductQuantity(1);
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("a0558e9f-1c39-460e-8860-71af6af63bd6");
        product2.setProductName("Kecap Bango");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product editedProduct = new Product();
        product2.setProductId("fa558e9f-1c39-460e-8860-71af6af63bd6");
        product2.setProductName("NONO");
        product2.setProductQuantity(20);

        Product result = productRepository.edit(editedProduct);
        assertNull(result);
    }

    @Test
    void testEditAndDelete() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(product1.getProductId());
        product2.setProductName("Sampo Cap Bambang Siapa?");
        product2.setProductQuantity(10);
        Product editedProduct = productRepository.edit(product2);

        assertNotNull(editedProduct);
        productRepository.delete(editedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product product = new Product();
        product.setProductName("Siapa?");
        product.setProductQuantity(1);
        productRepository.create(product);

        productRepository.delete(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }


    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("fa558e9f-1c39-460e-8860-71af6af63bd6");
        product2.setProductName("Kecap Bango");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product foundProduct = productRepository.getProductById("fa558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundProduct);
        assertEquals("Kecap Bango", foundProduct.getProductName());
        assertEquals(20, foundProduct.getProductQuantity());
    }

    @Test
    void testGetProductByIdNotFound() {
        Product foundProduct = productRepository.getProductById("");
        assertNull(foundProduct);
    }
}


