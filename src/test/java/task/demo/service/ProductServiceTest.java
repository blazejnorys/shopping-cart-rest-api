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
import task.scRestApi.service.ProductService;
import task.scRestApi.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScRestApiApplication.class})
public class ProductServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProductService productService;

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
    public void shouldFindOneProduct(){
        //given
        //when
        Product productById = productService.getProductById(kiwi.getId());
        //then
        Assertions.assertThat(productById.getCode()).isEqualTo("KIWI");
    }

    @Test
    public void shouldFindAllProducts(){
        //given
        //when
        List<Product> productList = productService.findAll();
        //then
        Assertions.assertThat(productList.size()).isEqualTo(14);
    }

    @Test
    public void shouldTakeOneProduct(){
        //given
        //when
        productService.getProductAndUpdateQuantiy(guava);
        productService.getProductAndUpdateQuantiy(guava);
        productService.getProductAndUpdateQuantiy(guava);
        Product guavaUpdated = productService.getProductById(guava.getId());
        //then
        Assertions.assertThat(guavaUpdated.getQuantity()).isEqualTo(120);
    }

    @Test
    public void shouldAddOneProduct(){
        //given
        //when
        productService.removeProductAndUpdateQuantity(strawberry);
        productService.removeProductAndUpdateQuantity(strawberry);
        productService.removeProductAndUpdateQuantity(strawberry);
        Product strawberryUpdated = productService.getProductById(strawberry.getId());
        //then
        Assertions.assertThat(strawberryUpdated.getQuantity()).isEqualTo(126);

    }

}

