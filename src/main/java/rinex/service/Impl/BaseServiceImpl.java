package rinex.service.Impl;

import rinex.dao.BaseRepository;
import rinex.model.BaseModel;
import rinex.service.BaseService;

abstract class BaseServiceImpl <T extends BaseModel, P extends BaseRepository<T>> implements BaseService {

   protected P dao;

    public void setPersistence(P dao){
        this.dao = dao;
    }
}
