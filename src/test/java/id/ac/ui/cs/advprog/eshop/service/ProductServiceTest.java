package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        sampleProduct.setProductName("Shampoo");
        sampleProduct.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);

        Product createdProduct = productService.create(sampleProduct);

        assertEquals(sampleProduct, createdProduct);
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testEdit() {
        when(productRepository.edit(sampleProduct)).thenReturn(sampleProduct);

        Product editedProduct = productService.edit(sampleProduct);

        assertEquals(sampleProduct, editedProduct);
        verify(productRepository, times(1)).edit(sampleProduct);
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(sampleProduct);

        productService.delete(sampleProduct);

        verify(productRepository, times(1)).delete(sampleProduct);
    }

    @Test
    void testGetProductById() {
        when(productRepository.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(sampleProduct);

        Product foundProduct = productService.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(sampleProduct, foundProduct);
        verify(productRepository, times(1)).getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(sampleProduct, result.get(0));
        verify(productRepository, times(1)).findAll();
    }
}
