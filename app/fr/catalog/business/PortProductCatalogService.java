package fr.catalog.business;

import java.util.List;
import java.util.Optional;

public interface PortProductCatalogService {

    void registerNewProduct(Product product);

    List<Product> allProducts();

    Optional<Product> findProductByEan(String ean);

    void empty();

    void updateProduct(Product product);

    void deleteProduct(String ean);

    void loadFromFile(String filename);
}
