package rinex.exception;

public class InvalidHeaderLabelException extends RuntimeException {

    public InvalidHeaderLabelException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
