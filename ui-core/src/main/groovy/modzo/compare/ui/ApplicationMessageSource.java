package modzo.compare.ui;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ApplicationMessageSource {
    private final MessageSource messageSource;

    public ApplicationMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Locale locale, Object... vars){
        return messageSource.getMessage(code, vars, locale);
    }
}
