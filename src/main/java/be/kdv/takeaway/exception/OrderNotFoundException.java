package be.kdv.takeaway.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){ super("Order(s) not found"); }

    public OrderNotFoundException(String id){
        super("Order with ID: " + id + " not found");
    }
}
