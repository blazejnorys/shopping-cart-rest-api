package task.scRestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "shopping_cart_product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShoppingCartProduct {

    @Id
    @SequenceGenerator(name = "ShoppingCartProductS", sequenceName = "shopping_cart_product_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ShoppingCartProductS")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ShoppingCartProduct(ShoppingCart shoppingCart, Product product) {
        this.shoppingCart = shoppingCart;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
