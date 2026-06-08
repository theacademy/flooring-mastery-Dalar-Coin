package flooringmastery.service;

public class FlooringMasteryDuplicateIdException extends Exception {

    public FlooringMasteryDuplicateIdException(String message) {
        super(message);
    }

    public FlooringMasteryDuplicateIdException(String message,
                                               Throwable cause) {
        super(message, cause);
    }

}
