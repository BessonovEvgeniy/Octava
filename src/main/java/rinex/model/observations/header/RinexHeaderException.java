package rinex.model.observations.header;

public class RinexHeaderException extends RuntimeException {

    private String message;

    public RinexHeaderException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
