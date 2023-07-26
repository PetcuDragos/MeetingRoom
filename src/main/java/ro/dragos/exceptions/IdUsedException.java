package ro.dragos.exceptions;

public class IdUsedException extends RuntimeException {
    public IdUsedException() {
        super();
    }
    public IdUsedException(String message) {
        super(message);
    }
}
