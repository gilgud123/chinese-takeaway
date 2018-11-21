package be.kdv.takeaway.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private String entityIdString;
    private int entityIdNr;
    private Class entityClass;
    private String message;

    public EntityNotFoundException(final String entityId, final Class entityClass) {
        this.entityIdString = entityId;
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " with the identifier " + entityId + " not found.";
    }

    public EntityNotFoundException(final Class entityClass){
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " not found.";
    }

    public EntityNotFoundException(final int entityId, final Class entityClass) {
        this.entityIdNr = entityId;
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " with the identifier " + entityId + " not found.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
