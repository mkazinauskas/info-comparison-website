package modzo.compare.command.core.domain.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UniqueIdValidator {

    public void validate(String uniqueId) {
        if (StringUtils.isBlank(uniqueId)) {
            throw new DomainException("UNIQUE_ID_IS_BLANK", "Unique id cannot be blank.");
        }
        if (uniqueId.length() != 40) {
            throw new DomainException("UNIQUE_ID_IS_LENGTH_IS_NOT_CORRECT", "Unique id has to be 40 characters length.");
        }
    }

}
