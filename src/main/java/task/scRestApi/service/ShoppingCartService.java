package task.scRestApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.scRestApi.dto.ShoppingCartDTO;
import task.scRestApi.model.Product;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.ShoppingCartProduct;
import task.scRestApi.model.User;
import task.scRestApi.repository.ShoppingCartRepository;

import java.math.BigDecimal;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartProductService shoppingCartProductService;


    public ShoppingCartDTO getShoppingCartForUser(User user) {
        ShoppingCart scUser = shoppingCartRepository.findShoppingCartByUser(user);
        return mapShoppingCartToDTO(scUser);
    }

    public ShoppingCart getShoppingCartByUserId(Integer userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId);
    }

    private ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO addProductToShoppingCart(Integer userId, Product product) {
        Product productUpdated = productService.getProductAndUpdateQuantiy(product);
        ShoppingCart scUser = getShoppingCartByUserId(userId);
        createShoppingCartProduct(productUpdated, scUser);
        updateTotalPriceOfShoppingCart(scUser, productUpdated, false);
        scUser = saveShoppingCart(scUser);
        return mapShoppingCartToDTO(scUser);
    }

    public ShoppingCartDTO removeProductFromShoppingCart(Integer userId, Product product) {
        ShoppingCart scUser = getShoppingCartByUserId(userId);
        removeShoppingCartProduct(product, scUser);
        updateTotalPriceOfShoppingCart(scUser, product, true);
        scUser = saveShoppingCart(scUser);
        return mapShoppingCartToDTO(scUser);
    }

    private void removeShoppingCartProduct(Product product, ShoppingCart scUser) {
        ShoppingCartProduct scp = shoppingCartProductService.findShoppingCartProduct(product, scUser);
        scUser.getShoppingCartProductList().remove(scp);
    }

    private void createShoppingCartProduct(Product productUpdated, ShoppingCart scUser) {
        ShoppingCartProduct shoppingCardProduct = new ShoppingCartProduct(scUser, productUpdated);
        scUser.getShoppingCartProductList().add(shoppingCardProduct);
    }

    private void updateTotalPriceOfShoppingCart(ShoppingCart scUser, Product productUpdated, boolean isRemoving) {
        BigDecimal totalPriceOfShoppingCart = scUser.getTotalPrice() == null ? new BigDecimal(0) : scUser.getTotalPrice();
        if (isRemoving) {
            scUser.setTotalPrice(totalPriceOfShoppingCart.subtract(productUpdated.getPrice()));
            return;
        }
        scUser.setTotalPrice(totalPriceOfShoppingCart.add(productUpdated.getPrice()));
    }

    private ShoppingCartDTO mapShoppingCartToDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartDTO.setUserLogin(shoppingCart.getUser().getLogin());
        for (ShoppingCartProduct shoppingCartProduct : shoppingCart.getShoppingCartProductList()) {
            shoppingCartDTO.getProductList().add(shoppingCartProduct.getProduct());
        }
        return shoppingCartDTO;
    }

    public boolean isUserAvailableToRemoveThisProduct(Integer userId, Product product) {
        ShoppingCart scUser = getShoppingCartByUserId(userId);
        ShoppingCartProduct shoppingCartProduct = shoppingCartProductService.findShoppingCartProduct(product, scUser);
        return shoppingCartProduct != null;
    }
}
