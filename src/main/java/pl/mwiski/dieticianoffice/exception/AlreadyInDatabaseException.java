package pl.mwiski.dieticianoffice.exception;

public class AlreadyInDatabaseException extends RuntimeException {
    public AlreadyInDatabaseException(String message) {
        super(message);
    }
}
