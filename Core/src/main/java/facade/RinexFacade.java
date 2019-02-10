package facade;

import converter.AbstractPopulatingConverter;
import dto.RinexFileDto;
import model.StoredFileModel;
import model.observation.ReceiverDataModel;
import model.rinex.RinexFileModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import service.RinexFileService;
import service.StorageService;
import service.impl.rinex.ReadHeaderImpl;

import javax.annotation.Resource;
import javax.inject.Provider;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RinexFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RinexFacade.class);

    private static final String ERR_STORE_MSG = "Can't store Rinex file %s";
    private static final String ERR_INSERT_MSG = "Can't create " + RinexFileModel.class.getCanonicalName();

    @Resource(name = "localStorage")
    private StorageService storageService;

    @Resource(name = "rinexFileService")
    private RinexFileService rinexFileService;

    @Resource(name = "rinexFileConverter")
    private AbstractPopulatingConverter<RinexFileModel, RinexFileDto> converter;

    @Autowired
    private Provider<ReceiverDataModel> rdmProvider;

    @Autowired
    private ReadHeaderImpl readHeader;


    public RinexFileDto convert(RinexFileModel rinexFileModel) {
        return converter.convert(rinexFileModel);
    }

    public RinexFileDto store(MultipartFile file) {
        RinexFileModel rinexFileModel = null;

        try {
            StoredFileModel storedFile = storageService.store(file);

            rinexFileModel = new RinexFileModel();
            rinexFileModel.setStoredFile(storedFile);
            rinexFileService.insert(rinexFileModel);

        } catch (SQLException e) {
            LOG.error(ERR_INSERT_MSG);
        } catch (FileUploadException e) {
            LOG.error(String.format(ERR_STORE_MSG, file.getName()));
        } finally {
            RinexFileDto rinexFileDto = convert(rinexFileModel);
            return Optional.ofNullable(rinexFileDto).orElse(null);
        }
    }

    public List<RinexFileDto> store(List<MultipartFile> files) {

        List<RinexFileDto> rinexFiles = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(files)) {
            rinexFiles = files.stream()
                    .filter(Objects::nonNull)
                    .map(file -> store(file))
                    .collect(Collectors.toList());
        }

        return rinexFiles;
    }

    //    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
//        context.register(MvcConfiguration.class);
//        context.register(BusinessHibernateConfig.class);
//        context.registerBean(LogInjector.class);
//        context.scan("ppa", "utils", "config");
//
//        ReceiverDataModel rdm = context.getBean(ReceiverDataModel.class);
//
////        Assert.isTrue(model1 != model2, "Should be diff objects");
//    }
}
