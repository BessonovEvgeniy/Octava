package octava.exception;

public class RestException extends RuntimeException {

    public RestException() {
        super();
    }

    public RestException(String msg) {
        super(msg);
    }
}
