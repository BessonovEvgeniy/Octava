package octava.service;

import octava.model.rinex.RinexFileMediaModel;

import java.io.File;

public interface RinexMediaService extends MediaService<RinexFileMediaModel> {

    RinexFileMediaModel getRinexFileByPath(final File path);
}
