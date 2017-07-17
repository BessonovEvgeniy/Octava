package rinex.service.Impl;

import rinex.dao.BaseRepository;
import rinex.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.BaseService;

public abstract class BaseServiceImpl <T extends BaseModel, P extends BaseRepository<T>> implements BaseService {

   protected P dao;

    @Autowired
    public void setPersistence(P dao){
        this.dao = dao;
    }
}
