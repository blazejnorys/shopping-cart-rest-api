package task.scRestApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.scRestApi.model.Product;
import task.scRestApi.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final static String PRODUCT_NO_LONGER_AVAILABLE = "Sorry! This product is no longer available";

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    public Product updateExistingProduct(Product product) {
        Product prodctToUpdate = updateProductAndSave(product);
        return productRepository.save(prodctToUpdate);
    }

    public List<Product> findAllProductsWithQuantityBiggerThanZero() {
        return productRepository.findAllProductsWithQuantityBiggerThanZero();
    }

    public Product getProductAndUpdateQuantiy(Product product) {
        Product productUpdated = getProductById(product.getId());
        if (isSelectedProductAvailable(productUpdated)) {
            return decreaseQuantityOfProductByOne(productUpdated);
        }
        throw new IllegalStateException(PRODUCT_NO_LONGER_AVAILABLE);
    }

    public void removeProductAndUpdateQuantity(Product product) {
        Product productToUpdate = getProductById(product.getId());
        productToUpdate.setQuantity(productToUpdate.getQuantity() + 1);
        saveProduct(productToUpdate);
    }

    public Product getProductById(Integer productId) {
        return productRepository.getOne(productId);
    }

    private boolean isSelectedProductAvailable(Product product) {
        return product.getQuantity() > 0;
    }

    private Product decreaseQuantityOfProductByOne(Product product) {
        product.setQuantity(product.getQuantity() - 1);
        return saveProduct(product);
    }

    private Product updateProductAndSave(Product product) {
        Product prodctToUpdate = getProductById(product.getId());
        prodctToUpdate.setCode(product.getCode());
        prodctToUpdate.setDescription(product.getDescription());
        prodctToUpdate.setQuantity(product.getQuantity());
        prodctToUpdate.setPrice(product.getPrice());
        return prodctToUpdate;
    }
}
