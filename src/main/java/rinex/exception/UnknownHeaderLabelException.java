package rinex.exception;

public class UnknownHeaderLabelException extends RuntimeException {

    private String message;

    public UnknownHeaderLabelException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
