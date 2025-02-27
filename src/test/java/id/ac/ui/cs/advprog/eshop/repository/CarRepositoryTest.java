package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        Car createdCar = carRepository.create(car);
        assertNotNull(createdCar.getCarId());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("dd558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Honda");
        Car createdCar = carRepository.create(car);
        assertEquals("dd558e9f-1c39-460e-8860-71af6af63bd6", createdCar.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        Car car2 = new Car();
        carRepository.create(car1);
        carRepository.create(car2);
        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testFindById() {
        Car car1 = new Car();
        car1.setCarName("Toyota");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Ford");
        carRepository.create(car2);

        Car car3 = new Car();
        car3.setCarName("Honda");
        Car createdCar3 = carRepository.create(car3);

        Car foundCar = carRepository.findById(createdCar3.getCarId());
        assertNotNull(foundCar);
        assertEquals("Honda", foundCar.getCarName());
    }

    @Test
    void testFindById_NotFound() {
        assertNull(carRepository.findById(""));
    }

    @Test
    void testUpdate() {
        Car car1 = new Car();
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Ford");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);
        Car createdCar2 = carRepository.create(car2);

        Car updatedCar = new Car();
        updatedCar.setCarName("Ford??");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update(createdCar2.getCarId(), updatedCar);
        assertNotNull(result);
        assertEquals("Ford??", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdate_NotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Nissan");
        assertNull(carRepository.update("", updatedCar));
    }

    @Test
    void testDelete() {
        Car car = new Car();
        Car createdCar = carRepository.create(car);
        carRepository.delete(createdCar.getCarId());
        assertNull(carRepository.findById(createdCar.getCarId()));
    }

    @Test
    void testDelete_NotFound() {
        carRepository.delete("");
    }
}
