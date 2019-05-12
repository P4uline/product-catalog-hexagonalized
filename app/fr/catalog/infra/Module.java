package fr.catalog.infra;

import com.google.inject.AbstractModule;
import fr.catalog.business.PortEventDao;
import fr.catalog.business.PortEventService;
import fr.catalog.business.EventServiceImpl;
import fr.catalog.infra.dao.EbeanEventDao;
import fr.catalog.infra.dao.EbeanProductDao;
import fr.catalog.infra.file.FilesystemGatewayAdapter;
import fr.catalog.business.*;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `fr.catalog.infra.Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(PortEventService.class).to(EventServiceImpl.class);
        bind(PortProductCatalogService.class).to(ProductServiceImpl.class);
        bind(PortEventDao.class).to(EbeanEventDao.class);
        bind(PortProductDao.class).to(EbeanProductDao.class);
        bind(PortFileGateway.class).to(FilesystemGatewayAdapter.class);
    }

}
