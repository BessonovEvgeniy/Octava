//package validator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.Errors;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.doReturn;
//import static validator.RinexFileValidator.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RinexFileModelValidatorUnitTest {
//
//    private static final String EXPECTED_RINEX_FILE_NAME = "EXPECTED_RINEX_FILE_NAME.18o";
//    private static final Long EXPECTED_RINEX_FILE_SIZE = MAX_RINEX_SIZE - 1L;
//
//    private static final String INVALID_RINEX_FILE_NAME = "EXPECTED_RINEX_FILE_NAME.18n";
//
//    private RinexFileValidator rinexFileValidator = new RinexFileValidator();
//
//    private Errors errors;
//
//    @Mock
//    private MultipartFile rinexFile;
//
//    @Before
//    public void init() {
//        doReturn(EXPECTED_RINEX_FILE_NAME).when(rinexFile).getName();
//        doReturn(EXPECTED_RINEX_FILE_SIZE).when(rinexFile).getSize();
//
//        errors = new BeanPropertyBindingResult(rinexFile, "rinexFile");
//    }
//
//    @Test
//    public void shouldContainRinexFileNameErrorCode() {
//        doReturn(INVALID_RINEX_FILE_NAME).when(rinexFile).getName();
//
//        rinexFileValidator.validate(rinexFile, errors);
//
//        assertTrue(errors.hasErrors());
//        assertTrue(getAllErrorCodes(errors).contains(INVALID_RINEX_FILE_NAME_ERROR_CODE));
//    }
//
//    @Test
//    public void shouldContainZeroFileSizeErrorCode() {
//        doReturn(0L).when(rinexFile).getSize();
//
//        rinexFileValidator.validate(rinexFile, errors);
//
//        assertTrue(errors.hasErrors());
//        assertTrue(getAllErrorCodes(errors).contains(RINEX_FILE_ZERO_SIZE_ERROR_CODE));
//    }
//
//    @Test
//    public void shouldContainMaximumSizeExceededErrorCode() {
//        doReturn(MAX_RINEX_SIZE + 1L).when(rinexFile).getSize();
//
//        rinexFileValidator.validate(rinexFile, errors);
//
//        assertTrue(errors.hasErrors());
//        assertTrue(getAllErrorCodes(errors).contains(RINEX_FILE_SIZE_EXCEEDED_ERROR_CODE));
//    }
//
//    private List<String> getAllErrorCodes(Errors errors) {
//        return errors.getAllErrors().stream().map(error -> error.getCode()).collect(Collectors.toList());
//    }
//}
