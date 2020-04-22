package octava.service.impl;

import octava.dao.RinexFileRepository;
import octava.dao.impl.RinexFileRepositoryImpl;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service("rinexMediaService")
public class RinexMediaServiceImpl extends MediaServiceImpl<RinexFileMediaModel, RinexFileRepositoryImpl> implements RinexMediaService {

    @Resource
    private RinexFileRepository rinexFileRepository;

    @Override
    public RinexFileMediaModel getRinexFileByPath(final File file) {

        return rinexFileRepository.findRinexFileByPath(file);
    }
}
