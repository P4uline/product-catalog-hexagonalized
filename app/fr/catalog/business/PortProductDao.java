package fr.catalog.business;

import java.util.List;
import java.util.Optional;

public interface PortProductDao {
    void save(Product product);

    List<Product> all();

    Optional<Product> findByEan(String ean);

    void flushAll();

    void delete(String ean);
}
