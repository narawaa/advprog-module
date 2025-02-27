package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarName("Toyota");
        when(carRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals("Toyota", createdCar.getCarName());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        Car car1 = new Car(); car1.setCarName("Toyota");
        Car car2 = new Car(); car2.setCarName("Honda");
        carList.add(car1);
        carList.add(car2);
        Iterator<Car> iterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getCarName());
        assertEquals("Honda", result.get(1).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarName("Ford");
        when(carRepository.findById("dd558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(car);

        Car foundCar = carService.findById("dd558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(foundCar);
        assertEquals("Ford", foundCar.getCarName());
        verify(carRepository, times(1)).findById("dd558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testUpdate() {
        Car car1 = new Car();
        car1.setCarName("BMW");
        Car car2 = new Car();
        car2.setCarName("Mercedes");
        when(carRepository.update("d2558e9f-1c39-460e-8860-71af6af63bd6", car2)).thenReturn(car2);

        carService.update("d2558e9f-1c39-460e-8860-71af6af63bd6", car2);

        verify(carRepository, times(1)).update("d2558e9f-1c39-460e-8860-71af6af63bd6", car2);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("dd558e9f-1c39-460e-8860-71af6af63bd6");

        carService.deleteCarById("dd558e9f-1c39-460e-8860-71af6af63bd6");

        verify(carRepository, times(1)).delete("dd558e9f-1c39-460e-8860-71af6af63bd6");
    }
}