package task.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import task.scRestApi.ScRestApiApplication;
import task.scRestApi.model.Product;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.User;
import task.scRestApi.service.ProductService;
import task.scRestApi.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScRestApiApplication.class})
public class ShoppingCartServiceTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    private Product kiwi = new Product("KIWI","Kiwi", BigDecimal.ONE,123);
    private Product orange = new Product("ORANGE","Pomarańcz", BigDecimal.ONE,123);
    private Product strawberry = new Product("STRAWBERRY","Truskawka", BigDecimal.ONE,123);
    private Product guava = new Product("GUAVA","Guawa", BigDecimal.ONE,123);

    @Before
    public void init(){
        productService.saveProduct(kiwi);
        productService.saveProduct(orange);
        productService.saveProduct(strawberry);
        productService.saveProduct(guava);
    }

    @Test
    public void shouldAddProductToShoppingCart(){
        //given
        //when
        shoppingCartService.addProductToShoppingCart(1,kiwi);
        shoppingCartService.addProductToShoppingCart(1,orange);
        shoppingCartService.addProductToShoppingCart(1,strawberry);
        shoppingCartService.addProductToShoppingCart(1,guava);
        ShoppingCart sc = shoppingCartService.getShoppingCartByUserId(1);
        //then
        Assertions.assertThat(sc.getShoppingCartProductList().size()).isEqualTo(4);
    }

    @Test
    public void shouldRemoveProductFromShoppingCart(){
        //given
        //when
        shoppingCartService.addProductToShoppingCart(1,kiwi);
        shoppingCartService.addProductToShoppingCart(1,orange);
        shoppingCartService.addProductToShoppingCart(1,strawberry);
        shoppingCartService.addProductToShoppingCart(1,guava);
        shoppingCartService.removeProductFromShoppingCart(1,kiwi);
        shoppingCartService.removeProductFromShoppingCart(1,orange);
        ShoppingCart sc = shoppingCartService.getShoppingCartByUserId(1);
        //then
        Assertions.assertThat(sc.getShoppingCartProductList().size()).isEqualTo(2);
    }
}
