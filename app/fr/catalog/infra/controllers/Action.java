package fr.catalog.infra.controllers;

import fr.catalog.business.Event;
import play.data.validation.Constraints;

public class Action {

    public Action(Type type, String ean, String owner) {
        this.type = type;
        this.ean = ean;
        this.owner = owner;
    }

    public static Action newAction(Action.Type type, String ean, String owner) {
        return new Action(type, ean, owner);
    }

    public static Action toAction(Event event) {
        return newAction(toActionType(event.type), event.ean, event.owner);
    }

    private static Type toActionType(Event.EventType eventType) {
        switch(eventType) {
            case CREATE_PRODUCT:
                return Type.CREATE_ITEM;
            case EDIT_PRODUCT:
                return Type.EDIT_ITEM;
            case UPDATE_PRODUCT:
                return Type.UPDATE_ITEM;
            case DELETE_PRODUCT:
                return Type.DELETE_ITEM;
            case FLUSH_DATABASE:
                return Type.FLUSH_DATABASE;
            case POPULATE_DATABASE_FROM_DATAFILE:
                return Type.POPULATE_DATABASE_FROM_DATAFILE;
            case CONSULT_ALL_PRODUCTS:
                return Type.CONSULT_ALL_ITEMS;
            case CONSULT_PRODUCT:
                return Type.CONSULT_ITEM;
            case CHANGE_USER_ACCESS:
                return Type.CHANGE_USER_ACCESS;
        }
        throw new IllegalStateException("Unknown type : '" + eventType + "'");
    }

    public enum Type {
        CREATE_ITEM,
        EDIT_ITEM,
        UPDATE_ITEM,
        DELETE_ITEM,
        FLUSH_DATABASE,
        POPULATE_DATABASE_FROM_DATAFILE,
        CONSULT_ALL_ITEMS,
        CONSULT_ITEM,
        CHANGE_USER_ACCESS;
    }

    @Constraints.Required
    public Action.Type type;

    @Constraints.Required
    public final String ean;

    @Constraints.Required
    public final String owner;

    public String toString() {
        return "[" + owner + " - " + ean + " - " + type.name() + "]";
    }
}
