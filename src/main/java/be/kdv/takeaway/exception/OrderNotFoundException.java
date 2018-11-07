package be.kdv.takeaway.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){ super("Order(s) not found"); }
}
