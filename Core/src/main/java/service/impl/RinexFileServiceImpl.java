package service.impl;

import dao.RinexFileRepository;
import model.rinex.RinexFileModel;
import org.springframework.stereotype.Service;
import service.RinexFileService;

@Service("rinexFileService")
public class RinexFileServiceImpl<T extends RinexFileModel, P extends RinexFileRepository<T>> extends BaseServiceImpl<T, P> implements RinexFileService<T> {

}
