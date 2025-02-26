package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage (Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost (@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage (@PathVariable String productId, Model model) {
        Product product = service.getProductById(productId);
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPost (@ModelAttribute Product product) {
        service.edit(product);
        return "redirect:list";
    }
  
    @PostMapping("/delete/{productId}")
    public String deleteProductPost (@PathVariable String productId) {
        Product product = service.getProductById(productId);
        service.delete(product);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage (Model model) {
        List <Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }
}

@Controller
@RequestMapping("/car")
class CarController extends ProductController {
    @Autowired
    private CarServiceImpl carService;

    private static final String REDIRECT_LIST_CAR = "redirect:listCar";

    @GetMapping("/createCar")
    public String createCarPage (Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "CreateCar";
    }

    @PostMapping("/createCar")
    public String createCarPost (@ModelAttribute Car car, Model model) {
        carService.create(car);
        return REDIRECT_LIST_CAR;
    }

    @GetMapping("/listCar")
    public String carListPage (Model model) {
        List <Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "CarList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage (@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "EditCar";
    }

    @PostMapping("/editCar")
    public String editCarPost (@ModelAttribute Car car, Model model) {
        carService.update(car.getCarId(), car);
        return REDIRECT_LIST_CAR;
    }

    @PostMapping("/deleteCar")
    public String deleteCar (@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return REDIRECT_LIST_CAR;
    }
}