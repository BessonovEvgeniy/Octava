package dao.jpa;

import dao.RinexFileRepository;
import model.rinex.RinexFileModel;
import org.springframework.stereotype.Repository;

@Repository
public class RinexFileRepositoryImpl extends BaseRepositoryImpl<RinexFileModel> implements RinexFileRepository<RinexFileModel> {

}
