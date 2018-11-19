package task.scRestApi.dto;

import lombok.Data;
import task.scRestApi.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCartDTO {

    private String userLogin;
    private List<Product> productList;
    private BigDecimal totalPrice;

    public ShoppingCartDTO() {
        this.productList = new ArrayList<>();
    }
}
