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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScRestApiApplication.class})
@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private MockMvc mvc;
    private MediaType mediaType = MediaType.APPLICATION_JSON;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        Product product = new Product("PASTA", "Makaron pełnoziarnisty", new BigDecimal(100), 50);
        productService.saveProduct(product);
    }

    @Test
    public void shouldReturnAllProducts() throws Exception {
        mvc.perform(get("/product/getAllProducts")
                .contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(mediaType))
                .andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].code", is("MILK_F")))
                .andExpect(jsonPath("$[2].description", is("Mleko pełnotłuste")))
                .andExpect(jsonPath("$[3].price", is(1.5)))
                .andExpect(jsonPath("$[4].quantity", is(100)));
    }

    @Test
    public void shouldReturnOneProduct() throws Exception {
        mvc.perform(get("/product/getProduct/1")
                .contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(mediaType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("MILK_L")));
    }

    @Test
    public void shouldAddNewProduct() throws Exception {
        Product product = new Product("KIWI", "Kiwi", new BigDecimal(2), 55);
        String productJson = objectMapper.writeValueAsString(product);

        mvc.perform(post("/product/saveProduct")
                .contentType(mediaType)
                .content(productJson)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("KIWI")))
                .andExpect(jsonPath("$.description", is("Kiwi")))
                .andExpect(jsonPath("$.price", is(2)));
    }

    @Test
    public void shouldUpdateExistingProduct() throws Exception {
        Product product = productService.getProductById(1);
        product.setDescription("Updated");
        product.setCode("UPDATED");
        String productJson = objectMapper.writeValueAsString(product);

        mvc.perform(put("/product/updateExistingProduct")
                .contentType(mediaType)
                .content(productJson)
                .accept(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("UPDATED")))
                .andExpect(jsonPath("$.description", is("Updated")))
                .andExpect(jsonPath("$.id", is(1)));
    }

}
