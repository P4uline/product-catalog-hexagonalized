package fr.catalog.infra.dao;

import fr.catalog.business.Event;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static fr.catalog.business.Event.newEvent;

@Entity
public class EventEntity extends Model {


    public static EventEntity newEventEntity(EventType eventType, String ean, String owner) {
        return new EventEntity(eventType, ean, owner);
    }

    public static Finder<Long, EventEntity> find = new Finder<>(EventEntity.class);

    public static List<EventEntity> findAll() {
        return find.all();
    }

    public static Event toEvent(EventEntity e) {
        return newEvent(toEventType(e.type), e.ean, e.owner);
    }

    private static Event.EventType toEventType(EventEntity.EventType type) {
        switch (type) {
            case CREATE_PRODUCT:
                return Event.EventType.CREATE_PRODUCT;
            case EDIT_PRODUCT:
                return Event.EventType.EDIT_PRODUCT;
            case DELETE_PRODUCT:
                return Event.EventType.DELETE_PRODUCT;
            case FLUSH_DATABASE:
                return Event.EventType.FLUSH_DATABASE;
            case POPULATE_DATABASE_FROM_DATAFILE:
                return Event.EventType.POPULATE_DATABASE_FROM_DATAFILE;
            case CONSULT_ALL_PRODUCTS:
                return Event.EventType.CONSULT_ALL_PRODUCTS;
            case CONSULT_PRODUCT:
                return Event.EventType.CONSULT_PRODUCT;
            case CHANGE_USER_ACCESS:
                return Event.EventType.CHANGE_USER_ACCESS;
        }
        throw new IllegalStateException("Unknown type : '" + type + "'");
    }

    public static EventType toEventEntityType(Event.EventType type) {
        switch (type) {
            case CREATE_PRODUCT:
                return EventEntity.EventType.CREATE_PRODUCT;
            case EDIT_PRODUCT:
                return EventEntity.EventType.EDIT_PRODUCT;
            case UPDATE_PRODUCT:
                return EventEntity.EventType.EDIT_PRODUCT;
            case DELETE_PRODUCT:
                return EventEntity.EventType.DELETE_PRODUCT;
            case FLUSH_DATABASE:
                return EventEntity.EventType.FLUSH_DATABASE;
            case POPULATE_DATABASE_FROM_DATAFILE:
                return EventEntity.EventType.POPULATE_DATABASE_FROM_DATAFILE;
            case CONSULT_ALL_PRODUCTS:
                return EventEntity.EventType.CONSULT_ALL_PRODUCTS;
            case CONSULT_PRODUCT:
                return EventEntity.EventType.CONSULT_PRODUCT;
            case CHANGE_USER_ACCESS:
                return EventEntity.EventType.CHANGE_USER_ACCESS;
        }
        throw new IllegalStateException("Unknown type : '" + type + "'");
    }

    public enum EventType {
        CREATE_PRODUCT,
        EDIT_PRODUCT, 
        FLUSH_DATABASE, 
        POPULATE_DATABASE_FROM_DATAFILE, 
        CONSULT_ALL_PRODUCTS,
        CONSULT_PRODUCT,
        CHANGE_USER_ACCESS,
        DELETE_PRODUCT;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Constraints.Required
    public EventType type;

    @Constraints.Required
    private final String ean;

    @Constraints.Required
    public final String owner;
    
    protected EventEntity(EventType eventType, String ean, String owner) {
        this.type = eventType;
        this.ean = ean;
        this.owner = owner;
    }
    
    public String toString() {
        return "[" + owner + " - " + ean + " - " + type.name() + "]";
    }
}
