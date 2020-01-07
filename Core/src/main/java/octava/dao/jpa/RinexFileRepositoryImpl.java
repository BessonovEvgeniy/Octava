package octava.dao.jpa;

import octava.dao.RinexFileRepository;
import octava.model.rinex.RinexFileModel;
import org.springframework.stereotype.Repository;

@Repository
public class RinexFileRepositoryImpl extends BaseRepositoryImpl<RinexFileModel> implements RinexFileRepository<RinexFileModel> {

}
