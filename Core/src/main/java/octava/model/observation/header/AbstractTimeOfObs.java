package octava.model.observation.header;

import lombok.Data;

import java.text.SimpleDateFormat;

public @Data class AbstractTimeOfObs implements HeaderLabel {

    private String system;

    private SimpleDateFormat timeOfObs;

    @Override
    public String toString() {
        return "System=" + system + " Time Of Observations=" + timeOfObs;
    }
}
