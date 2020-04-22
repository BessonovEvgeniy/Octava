package octava.state;

import lombok.Data;
import octava.model.PreProcessingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public abstract class PreProcessingState {

    protected static final Logger LOG = LoggerFactory.getLogger(PreProcessingState.class);

    protected PreProcessingModel preProcessingModel;

    public PreProcessingState(final PreProcessingModel preProcessingModel) {
        this.preProcessingModel = preProcessingModel;
    }

    public void process() {
        beforeProcess();

        afterProcess();
    }

    protected void beforeProcess() {};

    protected void afterProcess() {};
}
