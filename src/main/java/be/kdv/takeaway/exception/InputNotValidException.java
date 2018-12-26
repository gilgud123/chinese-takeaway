package be.kdv.takeaway.exception;

public class InputNotValidException extends RuntimeException {

    private String message;

    public InputNotValidException(String message){

        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
