package octava.service.impl;

import octava.dao.impl.RinexFileRepositoryImpl;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexFileService;
import org.springframework.stereotype.Service;

@Service("rinexFileService")
public class RinexFileServiceImpl extends MediaServiceImpl<RinexFileMediaModel, RinexFileRepositoryImpl> implements RinexFileService {

}
