package fr.catalog.business;

import java.util.List;

public interface PortEventDao {
    List<Event> findAllEvent();

    void save(Event event);
}
