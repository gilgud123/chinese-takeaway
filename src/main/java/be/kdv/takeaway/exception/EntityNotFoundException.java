package be.kdv.takeaway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String entityId;
    private int mealNum;
    private Class<?> entityClass;
    private String message;

    public EntityNotFoundException(final String entityId, final Class<?> entityClass) {
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " with the id " + entityId + " not found.";
    }

    public EntityNotFoundException(final int mealNum, final Class<?> entityClass) {
        this.mealNum = mealNum;
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " with the number " + mealNum + " not found.";
    }

    public EntityNotFoundException(final Class<?> entityClass){
        this.entityClass = entityClass;
        this.message = "Entity of type " + entityClass.getSimpleName() + " not found.";
    }

    public EntityNotFoundException(final List<Class<?>> entityList){
        this.entityClass = entityList.getClass();
        this.message = "Entity list of type " + entityClass.getSimpleName() + " not found.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getEntityId() {
        return entityId;
    }

    public int getMealNum() {
        return mealNum;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
