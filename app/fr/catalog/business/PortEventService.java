package fr.catalog.business;

import java.util.List;

public interface PortEventService {
    List<Event> findEvents();

    void createNewEvent(Event.EventType type, String ean, String name);
}
