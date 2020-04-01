package octava.facade;

import octava.converter.AbstractPopulatingConverter;
import octava.dto.RinexFileDto;
import octava.model.media.MediaModel;
import octava.model.observation.ReceiverDataModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexFileService;
import octava.service.StorageService;
import octava.service.impl.rinex.ReadHeaderImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.inject.Provider;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RinexFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RinexFacade.class);

    private static final String ERR_STORE_MSG = "Can't store Rinex file %s";
    private static final String ERR_INSERT_MSG = "Can't create " + RinexFileMediaModel.class.getCanonicalName();

    @Resource
    private StorageService storageService;

    @Resource
    private RinexFileService rinexFileService;

    @Resource(name = "rinexFileConverter")
    private AbstractPopulatingConverter<RinexFileMediaModel, RinexFileDto> rinexFileConverter;

    @Autowired
    private Provider<ReceiverDataModel> rdmProvider;

    @Autowired
    private ReadHeaderImpl readHeader;


    public RinexFileDto convert(RinexFileMediaModel rinexFileMediaModel) {
        return rinexFileConverter.convert(rinexFileMediaModel);
    }

    public RinexFileDto store(MultipartFile file) {
        RinexFileMediaModel rinexFileMediaModel = null;

        try {
            MediaModel media = storageService.store(file);

            rinexFileMediaModel = new RinexFileMediaModel();
//            rinexFileModel.setStoredFile(storedFile);
            rinexFileService.insert(rinexFileMediaModel);
        } finally {
            RinexFileDto rinexFileDto = convert(rinexFileMediaModel);
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
//        context.scan("ppa", "utils", "octava.config");
//
//        ReceiverDataModel rdm = context.getBean(ReceiverDataModel.class);
//
////        Assert.isTrue(model1 != model2, "Should be diff objects");
//    }
}
