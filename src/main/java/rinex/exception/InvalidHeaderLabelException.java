package rinex.exception;

public class InvalidHeaderLabelException extends RuntimeException {

    private String message;

    public InvalidHeaderLabelException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
