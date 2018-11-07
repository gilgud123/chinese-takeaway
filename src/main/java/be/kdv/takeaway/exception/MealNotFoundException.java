package be.kdv.takeaway.exception;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException() { super("Meal(s) not found"); }
}
