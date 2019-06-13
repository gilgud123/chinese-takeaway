package be.kdv.takeaway.exception;

public class InputNotValidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Class<?> entityClass;
    private String message;

    public InputNotValidException(String message){
        this.message = message;
    }

    public InputNotValidException(Class<?> entityClass){
        this.setEntityClass(entityClass);
        this.message = "Input for the entity of type " + entityClass.getSimpleName() + " is not valid.";
    }

    public InputNotValidException(Class<?> entityClass, String message){
        this.setEntityClass(entityClass);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }
}
