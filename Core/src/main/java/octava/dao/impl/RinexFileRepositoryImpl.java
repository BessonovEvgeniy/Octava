package octava.dao.impl;

import octava.dao.RinexFileRepository;
import octava.model.rinex.RinexFileMediaModel;
import org.springframework.stereotype.Repository;

@Repository("rinexFileRepository")
public class RinexFileRepositoryImpl extends MediaRepositoryImpl<RinexFileMediaModel> implements RinexFileRepository {

}
