package fr.catalog.infra.dao;

import fr.catalog.business.Event;
import fr.catalog.business.PortEventDao;
import io.ebean.Finder;

import java.util.List;

import static fr.catalog.infra.dao.EventEntity.newEventEntity;
import static fr.catalog.infra.dao.EventEntity.toEvent;
import static java.util.stream.Collectors.toList;

public class EbeanEventDao implements PortEventDao {

    private Finder<Object, EventEntity> finder = new Finder<>(EventEntity.class);
    
    @Override
    public List<Event> findAllEvent() {
        return finder.all().stream().map(e -> toEvent(e)).collect(toList());
    }

    @Override
    public void save(Event event) {
        newEventEntity(EventEntity.toEventEntityType(event.getType()), event.getEan(), event.getOwner()).save();
    }
}
