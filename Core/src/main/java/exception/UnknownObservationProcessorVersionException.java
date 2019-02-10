package exception;

public class UnknownObservationProcessorVersionException extends RuntimeException {

    public UnknownObservationProcessorVersionException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
