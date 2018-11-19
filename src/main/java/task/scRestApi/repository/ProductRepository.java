package task.scRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.scRestApi.model.Product;
import task.scRestApi.model.User;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE p.quantity >0")
    List<Product> findAllProductsWithQuantityBiggerThanZero();

}
