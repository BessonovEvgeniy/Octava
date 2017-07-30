package rinex.service.Impl.observations.rinex.rinexImpl.header;

public class RinexHeaderException extends Exception {

    String msg;

    RinexHeaderException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
