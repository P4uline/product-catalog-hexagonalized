package fr.catalog.business;

public class Event {

    public static Event newEvent(Event.EventType type, String ean, String owner) {
        return new Event(type, ean, owner);
    }

    public enum EventType {
        CREATE_PRODUCT,
        EDIT_PRODUCT,
        UPDATE_PRODUCT,
        DELETE_PRODUCT,
        FLUSH_DATABASE,
        POPULATE_DATABASE_FROM_DATAFILE,
        CONSULT_ALL_PRODUCTS,
        CONSULT_PRODUCT,
        CHANGE_USER_ACCESS;
    }

    private Event.EventType type;

    private final String ean;

    private final String owner;

    protected Event(Event.EventType eventType, String ean, String owner) {
        this.type = eventType;
        this.ean = ean;
        this.owner = owner;
    }

    public String toString() {
        return "[" + owner + " - " + ean + " - " + type.name() + "]";
    }

    public EventType getType() {
        return type;
    }
    
    public String getEan() {
        return ean;
    }

    public String getOwner() {
        return owner;
    }
}
