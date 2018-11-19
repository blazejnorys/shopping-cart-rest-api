package task.scRestApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @SequenceGenerator(name = "UserS", sequenceName = "user_s",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserS")
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @OneToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
}
