package rinex.exception;

public class UnknownObservationProcessorVersionException extends RuntimeException {

    private String message;

    public UnknownObservationProcessorVersionException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
