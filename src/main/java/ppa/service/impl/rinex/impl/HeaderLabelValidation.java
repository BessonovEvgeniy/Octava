package ppa.service.impl.rinex.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import ppa.exception.InvalidHeaderLabelException;
import ppa.exception.RinexLineLengthMismatchException;
import ppa.model.observation.header.HeaderLabel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class HeaderLabelValidation {

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
            System.out.println(object);
            System.out.println(String.format("Number of Errors: %d", constraintViolations.size()));
            for (ConstraintViolation<Object> cv : constraintViolations) {
                System.out.println(String.format(
                        "Error! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
            }
            throw new InvalidHeaderLabelException(object.getClass().getSimpleName());
        }
    }
}