package be.kdv.takeaway.exception;

public class AllergyNotFoundException  extends RuntimeException {
    public AllergyNotFoundException(){ super("Allergy does not exist"); }
}
