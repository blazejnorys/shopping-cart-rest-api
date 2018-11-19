package task.demo.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import task.scRestApi.ScRestApiApplication;
import task.scRestApi.model.Product;
import task.scRestApi.service.ProductService;
import task.scRestApi.service.ShoppingCartService;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScRestApiApplication.class})
@AutoConfigureMockMvc
public class ShoppingCartController extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MockMvc mvc;
    private MediaType mediaType = MediaType.APPLICATION_JSON;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldAddProductToShoppingCart() throws Exception {
        Product product = new Product("KIWI", "Kiwi", new BigDecimal(2), 55);
        product = productService.saveProduct(product);
        String productJson = objectMapper.writeValueAsString(product);

        mvc.perform(put("/shoppingCart/1/addProductToShoppingCart")
                .contentType(mediaType)
                .content(productJson)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userLogin", is("kowalski")))
                .andExpect(jsonPath("$.totalPrice", is(2)))
                .andExpect(jsonPath("$.productList", hasSize(1)));
    }

    @Test
    public void shouldRemoveProductFromShoppingCart() throws Exception {
        Product product = new Product("KIWI", "Kiwi", new BigDecimal(5.55), 55);
        product = productService.saveProduct(product);
        BigDecimal priceOfOneKiwi = new BigDecimal(5.55);
        BigDecimal priceOfTwoKiwies = priceOfOneKiwi.add(priceOfOneKiwi);
        shoppingCartService.addProductToShoppingCart(1, product);
        shoppingCartService.addProductToShoppingCart(1, product);
        shoppingCartService.addProductToShoppingCart(1, product);
        String productJson = objectMapper.writeValueAsString(product);

        mvc.perform(put("/shoppingCart/1/removeProductFromShoppingCart")
                .contentType(mediaType)
                .content(productJson)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productList", hasSize(2)))
                .andExpect(jsonPath("$.totalPrice", is(priceOfTwoKiwies)));

    }

}
