package octava.service.impl;

import octava.dao.impl.RinexFileRepositoryImpl;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexMediaService;
import org.springframework.stereotype.Service;

@Service("rinexMediaService")
public class RinexMediaServiceImpl extends MediaServiceImpl<RinexFileMediaModel, RinexFileRepositoryImpl> implements RinexMediaService {

}
