package task.scRestApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.scRestApi.model.Product;
import task.scRestApi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/getProduct/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("getAllProductsAvailable")
    public List<Product> findAllProductsAvailable() {
        return productService.findAllProductsWithQuantityBiggerThanZero();
    }

    @PostMapping("/saveProduct")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/updateExistingProduct")
    public Product updateExistingProduct(@RequestBody Product product) {
        return productService.updateExistingProduct(product);
    }

    @PutMapping("/getProductAndUpdateQuantity")
    public Product getProductAndUpdateQuantity(@RequestBody Product product) {
        return productService.getProductAndUpdateQuantiy(product);
    }

    @PutMapping("/removeProductAndUpdateQuantity")
    public void removeProductAndUpdateQuantity(@RequestBody Product product) {
        productService.removeProductAndUpdateQuantity(product);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }
}
