package be.kdv.takeaway.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private String entityId;
    private Class entityClass;
    private String message;

    public EntityNotFoundException(final String entityId, final Class entityClass) {
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " with the id " + entityId + " not found.";
    }

    public EntityNotFoundException(final Class entityClass){
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " not found.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
