package common.exception;


public class SystemOperationException extends Exception {

    public SystemOperationException() {
        super();
    }

    public SystemOperationException(String message) {
        super(message);
    }

    public SystemOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemOperationException(Throwable cause) {
        super(cause);
    }
}
