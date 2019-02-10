package exception;

public class UnknownHeaderLabelException extends RuntimeException {

    public UnknownHeaderLabelException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}
