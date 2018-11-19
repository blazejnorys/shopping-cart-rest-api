package task.scRestApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.scRestApi.dto.ShoppingCartDTO;
import task.scRestApi.exception.ExceptionControllerAdvice;
import task.scRestApi.model.Product;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.User;
import task.scRestApi.service.ShoppingCartProductService;
import task.scRestApi.service.ShoppingCartService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    private final static String WRONG_PRODUCT = "This product is not available in your shopping cart";

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ExceptionControllerAdvice exceptionControllerAdvice;

    @GetMapping("/getShoppingCartForUser")
    public ShoppingCartDTO getShoppingCartForUser(@RequestBody User user) {
        return shoppingCartService.getShoppingCartForUser(user);
    }

    @PutMapping("/{userId}/addProductToShoppingCart")
    public ShoppingCartDTO addProductToShoppingCart(@PathVariable Integer userId, @RequestBody Product product) {
        return shoppingCartService.addProductToShoppingCart(userId, product);
    }

    @PutMapping("/{userId}/removeProductFromShoppingCart")
    public ShoppingCartDTO removeProductFromShoppingCart(@PathVariable Integer userId, @RequestBody Product product) {
        if (shoppingCartService.isUserAvailableToRemoveThisProduct(userId, product)) {
            return shoppingCartService.removeProductFromShoppingCart(userId, product);
        }
        throw new IllegalStateException(WRONG_PRODUCT);
    }
}
