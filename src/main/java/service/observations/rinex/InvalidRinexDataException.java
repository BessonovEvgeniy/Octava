package service.observations.rinex;

public class InvalidRinexDataException extends Exception {
    String msg = "";
    InvalidRinexDataException(String msg) {
        this.msg = msg;
    }
    InvalidRinexDataException() {}
}