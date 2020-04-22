package octava.dao.impl;

import octava.dao.RinexFileRepository;
import octava.model.rinex.RinexFileMediaModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.io.File;

@Repository("rinexFileRepository")
public class RinexFileRepositoryImpl extends MediaRepositoryImpl<RinexFileMediaModel> implements RinexFileRepository {

    @Override
    public RinexFileMediaModel findRinexFileByPath(final File path) {

        final Query query = em.createQuery("SELECT r FROM RinexFileMediaModel r WHERE r.path = :path AND r.fileName=:fileName", RinexFileMediaModel.class);

        query.setParameter("path", path.getParent());
        query.setParameter("fileName", path.getName());

        return (RinexFileMediaModel) query.getSingleResult();
    }

}
