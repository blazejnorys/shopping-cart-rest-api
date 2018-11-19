package task.scRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    @Query("SELECT u.shoppingCart FROM User u WHERE u = :user")
    ShoppingCart findShoppingCartByUser(@Param("user") User user);

    @Query("SELECT u.shoppingCart FROM User u WHERE u.id = :userId")
    ShoppingCart findShoppingCartByUserId(@Param("userId") Integer userId);
}
