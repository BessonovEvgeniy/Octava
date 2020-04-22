package octava.model;

import lombok.Data;
import octava.model.rinex.RinexFileMediaModel;

import java.util.List;

@Data
public class PreProcessingModel {

    private List<RinexFileMediaModel> rinexFiles;

}
