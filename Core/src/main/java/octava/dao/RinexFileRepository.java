package octava.dao;

import octava.model.rinex.RinexFileMediaModel;

import java.io.File;

public interface RinexFileRepository extends MediaRepository<RinexFileMediaModel> {

    RinexFileMediaModel findRinexFileByPath(final File path);
}
