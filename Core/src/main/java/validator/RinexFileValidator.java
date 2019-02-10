package validator;

import model.observation.ReceiverDataModel;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import service.impl.rinex.ReadHeaderImpl;

import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("rinexFileValidator")
public class RinexFileValidator implements Validator {

    public static final String INVALID_RINEX_FILE_NAME_ERROR_CODE = "rinexFileName.wrong";
    public static final String RINEX_FILE_SIZE_EXCEEDED_ERROR_CODE = "rinexFile.maximumSizeExceeded";
    public static final String RINEX_FILE_ZERO_SIZE_ERROR_CODE = "rinexFile.zeroSize";
    public static final String RINEX_FILE_CURRUPTED_ERROR_CODE = "rinexFile.corrupted";
    public static final long MAX_RINEX_SIZE = 21000000L;

    private static final Logger LOG = LoggerFactory.getLogger(RinexFileValidator.class);

    private static final String NAME = "name";
    private static final String CONTENT_TYPE = "contentType";
    private static final String SIZE = "size";
    private static final String FILE = "fileItem";
    private static final Pattern RINEX_FILE_PATTERN = Pattern.compile("^(\\w*).(\\d{2}o)$");
    private static final String REQUIRED_CONTENT_TYPE = "";

    @Autowired
    private Provider<ReceiverDataModel> rdmProvider;

    @Autowired
    private ReadHeaderImpl readHeader;

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MultipartFile rinexFile = (MultipartFile) o;

        LOG.info("Rinex header validation...");
        Matcher matcher = RINEX_FILE_PATTERN.matcher(rinexFile.getOriginalFilename());
        if (!matcher.find()) {
            String msg = "Illegal file name " + rinexFile.getOriginalFilename();
            LOG.warn(msg);
            errors.rejectValue(NAME, INVALID_RINEX_FILE_NAME_ERROR_CODE);
        }
        if (rinexFile.getContentType().equals(REQUIRED_CONTENT_TYPE)) {
            String msg = "Incompatible content type: " + rinexFile.getContentType() + " ";
            LOG.warn(msg);
            errors.rejectValue(CONTENT_TYPE, msg);
        }
        int fileSize = Long.compare(rinexFile.getSize(), MAX_RINEX_SIZE);
        if (fileSize > 0) {
            errors.rejectValue(SIZE, RINEX_FILE_SIZE_EXCEEDED_ERROR_CODE);
        } else if (rinexFile.getSize() == 0){
            errors.rejectValue(SIZE, RINEX_FILE_ZERO_SIZE_ERROR_CODE);
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(rinexFile.getInputStream()));
            ReceiverDataModel dataModel = rdmProvider.get();
            readHeader.read(br, dataModel);
            LOG.info("Rinex header is valid.");
        } catch (IOException e) {
            errors.rejectValue(FILE, RINEX_FILE_CURRUPTED_ERROR_CODE);
        }
    }
}
