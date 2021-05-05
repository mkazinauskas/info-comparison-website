package modzo.compare.ui.index;

import modzo.compare.ui.ApplicationMessageSource;
import modzo.compare.ui.common.CommonDataMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class IndexController {

    private final CommonDataMapper commonDataMapper;

    private final ApplicationMessageSource messageSource;

    public IndexController(CommonDataMapper commonDataMapper, ApplicationMessageSource messageSource) {
        this.commonDataMapper = commonDataMapper;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    String index(Model model, Locale locale) {
        commonDataMapper.addCommonData(model, locale);

        model.addAttribute("page.title",
                messageSource.getMessage("page.index.title", locale));
        model.addAttribute("page.description",
                messageSource.getMessage("page.index.description", locale));

        model.addAttribute("page.path", "/");

        model.addAttribute("page.index.h1",
                messageSource.getMessage("page.index.h1", locale));
        model.addAttribute("page.index.search.first.placeholder",
                messageSource.getMessage("page.index.search.first.placeholder", locale));
        model.addAttribute("page.index.search.second.placeholder",
                messageSource.getMessage("page.index.search.second.placeholder", locale));
        model.addAttribute("page.index.search.all.items.button",
                messageSource.getMessage("page.index.search.all.items.button", locale));

        return "index";
    }

}
