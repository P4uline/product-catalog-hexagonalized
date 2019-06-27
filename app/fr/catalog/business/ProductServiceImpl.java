package fr.catalog.business;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.Optional;

import static fr.catalog.business.Event.EventType.*;
import static fr.catalog.infra.web.Users.getCurrentUser;

public class ProductServiceImpl implements PortProductCatalogService {
    
    @Inject
    private PortEventService eventService;
    
    @Inject
    private PortProductDao productDao;
    
    @Inject
    private PortFileGateway fileGateway;
    
    @Override
    public void registerNewProduct(Product product) {
        eventService.createNewEvent(CREATE_PRODUCT, product.getEan(), getCurrentUser().getName());
        productDao.save(product);
    }

    @Override
    public List<Product> allProducts() {
        return productDao.all();
    }

    @Override
    public Optional<Product> findProductByEan(String ean) {
        eventService.createNewEvent(CONSULT_PRODUCT, ean, getCurrentUser().getName());
        return productDao.findByEan(ean);
    }

    @Override
    public void empty() {
        productDao.flushAll();
    }

    @Override
    public void updateProduct(Product product) {
        eventService.createNewEvent(EDIT_PRODUCT, product.getEan(), getCurrentUser().getName());
        productDao.save(product);
    }

    @Override
    public void deleteProduct(String ean) {
        eventService.createNewEvent(DELETE_PRODUCT, ean, getCurrentUser().getName());
        productDao.delete(ean);
    }

    @Override
    public void loadFromFile(String filename) {
        // Pas de typage
        File file = fileGateway.getFileByName(filename);
        
        
        productDao.flushAll();
        eventService.createNewEvent(FLUSH_DATABASE, "no ean", getCurrentUser().getName());

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(settings);

        // call beginParsing to read records one by one, iterator-style.
        parser.beginParsing(file);

        String[] row;
        while ((row = parser.parseNext()) != null) {
            final String ean = RandomStringUtils.randomAlphanumeric(10);
            final String name = row[0];
            final String description = row[1];
            
            final Product product = Product.newProduct(ean, name, description, null);
            productDao.save(product);
        }
        eventService.createNewEvent(POPULATE_DATABASE_FROM_DATAFILE, "no ean", getCurrentUser().getName());
        parser.stopParsing();
    }
}
