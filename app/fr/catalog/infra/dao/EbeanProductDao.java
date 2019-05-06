package fr.catalog.infra.dao;

import fr.catalog.business.Product;
import fr.catalog.business.PortProductDao;
import io.ebean.Finder;

import java.util.List;
import java.util.Optional;

import static fr.catalog.infra.dao.ProductEntity.*;
import static java.util.stream.Collectors.toList;

public class EbeanProductDao implements PortProductDao {
    
    private static Finder<Object, ProductEntity> finder = new Finder<>(ProductEntity.class);
    
    @Override
    public void save(Product product) {
        newProductEntity(product.ean, product.name, product.description).save();
    }

    @Override
    public List<Product> all() {
        return finder.all().stream().map(p -> toProduct(p)).collect(toList());
    }

    @Override
    public Optional<Product> findByEan(String ean) {
        return toProductEntity(findProductByEan(ean));
    }

    @Override
    public void flushAll() {
        finder.all().forEach(product -> product.delete());
    }

    @Override
    public void delete(String ean) {
        finder.deleteById(ean);
    }

    private static Optional<ProductEntity> findProductByEan(String ean) {
        return finder.query().where().eq("ean", ean).findOneOrEmpty();
    }
}
