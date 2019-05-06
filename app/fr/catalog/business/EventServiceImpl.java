package fr.catalog.business;

import fr.catalog.infra.controllers.Users;
import fr.catalog.infra.dao.EventEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl implements PortEventService {

    @Inject
    private PortEventDao eventDao;

    public List<Event> findEvents() {
        User currentUser = Users.getCurrentUser();
        return eventDao.findAllEvent().stream().filter(e -> {
            if (!currentUser.getRole().equals(User.Role.ADMIN)) {
                if (currentUser.getRole().equals(User.Role.GESTIONAIRE)) {
                    return !e.type.equals(EventEntity.EventType.CHANGE_USER_ACCESS) && e.owner.equals(currentUser.getName());  
                } else if (currentUser.getRole().equals(User.Role.SUPER_GESTIONNAIRE)) {
                    return !e.type.equals(EventEntity.EventType.CHANGE_USER_ACCESS);
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewEvent(Event.EventType type, String ean, String name) {
        eventDao.save(Event.newEvent(type, ean, name));
    }
}
