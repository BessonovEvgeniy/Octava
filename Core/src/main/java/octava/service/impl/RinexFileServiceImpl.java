package octava.service.impl;

import octava.dao.RinexFileRepository;
import octava.model.rinex.RinexFileModel;
import org.springframework.stereotype.Service;
import octava.service.RinexFileService;

@Service("rinexFileService")
public class RinexFileServiceImpl<T extends RinexFileModel, P extends RinexFileRepository<T>> extends BaseServiceImpl<T, P> implements RinexFileService<T> {

}
