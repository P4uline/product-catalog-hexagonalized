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

    public Event.EventType type;

    public final String ean;

    public final String owner;

    protected Event(Event.EventType eventType, String ean, String owner) {
        this.type = eventType;
        this.ean = ean;
        this.owner = owner;
    }

    public String toString() {
        return "[" + owner + " - " + ean + " - " + type.name() + "]";
    }
}
