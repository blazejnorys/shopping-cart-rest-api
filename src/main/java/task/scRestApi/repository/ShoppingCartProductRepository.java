package task.scRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.scRestApi.model.Product;
import task.scRestApi.model.ShoppingCart;
import task.scRestApi.model.ShoppingCartProduct;
import task.scRestApi.model.User;

import java.util.List;

@Repository
public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct,Integer> {

    @Query("SELECT scp FROM ShoppingCartProduct scp WHERE scp.product = :productId AND scp.shoppingCart = :shoppingCartId")
    List<ShoppingCartProduct> findShoppingCartProduct(@Param("productId") Product product, @Param("shoppingCartId") ShoppingCart scUser);
}
