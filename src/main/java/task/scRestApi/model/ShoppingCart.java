package task.scRestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShoppingCart {

    @Id
    @SequenceGenerator(name = "ShoppingCartS", sequenceName = "shopping_cart_s", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ShoppingCartS")
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingCartProduct> shoppingCartProductList;

    @OneToOne(mappedBy = "shoppingCart")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<ShoppingCartProduct> getShoppingCartProductList() {
        return shoppingCartProductList;
    }

    public void setShoppingCartProductList(Set<ShoppingCartProduct> shoppingCartProductList) {
        this.shoppingCartProductList = shoppingCartProductList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
