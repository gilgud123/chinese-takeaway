package be.kdv.takeaway.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{

    @Getter private String entityId;
    @Getter private Class entityClass;
    @Getter private String message;

    public EntityNotFoundException(final Class entityClass){
        this.entityClass = entityClass;
        this.message = ("Entity of type " + entityClass.getSimpleName() + " not found");
    }

    public EntityNotFoundException( final Class entityClass, final String entityId){
        this.entityClass = entityClass;
        this.entityId = entityId;
        this.message = ("Entity of type " + entityClass.getSimpleName() + " with ID " + entityId + " not found");
    }
}
