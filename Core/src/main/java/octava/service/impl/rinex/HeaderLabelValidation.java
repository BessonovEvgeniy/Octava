package octava.service.impl.rinex;


import octava.exception.InvalidHeaderLabelException;
import octava.exception.RinexLineLengthMismatchException;
import octava.model.observation.header.HeaderLabel;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class HeaderLabelValidation {

    private static final Logger log = LoggerFactory.getLogger(HeaderLabelValidation.class);

    public static void validateLineLength(Object object) {
        if (object instanceof String) {
            String line = (String) object;
            if (line.length() > HeaderLabel.RINEX_LINE_LENGTH) {
                throw new RinexLineLengthMismatchException(line);
            }
        }
    }

    public static void validate(Object object) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(object);

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            log.warn(object.toString());
            log.warn(String.format("Number of Errors: %d", constraintViolations.size()));
            for (ConstraintViolation<Object> cv : constraintViolations) {
                log.warn(String.format(
                        "Error! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
            }
            throw new InvalidHeaderLabelException(object.getClass().getSimpleName());
        }
    }
}
