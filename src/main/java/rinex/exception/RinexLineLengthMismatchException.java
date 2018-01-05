package rinex.exception;

import rinex.model.observation.header.HeaderLabel;

public class RinexLineLengthMismatchException extends RuntimeException {

    public RinexLineLengthMismatchException(String line) {
        super("Length line mismatch " + HeaderLabel.RINEX_LINE_LENGTH + " vs " + line.length() + " : " + line);
    }
}
