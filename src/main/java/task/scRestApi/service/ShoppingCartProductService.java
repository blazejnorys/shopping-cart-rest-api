package task.scRestApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.scRestApi.model.Product;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.ShoppingCartProduct;
import task.scRestApi.repository.ShoppingCartProductRepository;

import java.util.List;

@Service
public class ShoppingCartProductService {

    @Autowired
    private ShoppingCartProductRepository shoppingCartProductRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    public ShoppingCartProduct saveShoppingCartProduct(ShoppingCartProduct scp) {
        return shoppingCartProductRepository.save(scp);
    }

    ShoppingCartProduct findShoppingCartProduct(Product product, ShoppingCart scUser) {
        List<ShoppingCartProduct> shoppingCartProductList = shoppingCartProductRepository.findShoppingCartProduct(product, scUser);
        return shoppingCartProductList.isEmpty() ? null : shoppingCartProductList.get(0);
    }
}
