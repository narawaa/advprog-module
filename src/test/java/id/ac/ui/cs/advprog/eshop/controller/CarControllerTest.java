package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("dd558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        mockMvc.perform(post("/car/createCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = new ArrayList<>();
        when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attributeExists("cars"));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("dd558e9f-1c39-460e-8860-71af6af63bd6");

        when(carService.findById("dd558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/dd558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService, times(1)).findById("dd558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("dd558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(10);

        mockMvc.perform(post("/car/editCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).update(eq("dd558e9f-1c39-460e-8860-71af6af63bd6"), any(Car.class));
    }

    @Test
    void testDeleteCarPost() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "dd558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById("dd558e9f-1c39-460e-8860-71af6af63bd6");
    }
}
