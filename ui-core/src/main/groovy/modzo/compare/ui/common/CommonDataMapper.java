package modzo.compare.ui.common;

import modzo.compare.ui.ApplicationMessageSource;
import modzo.compare.ui.items.SeoComparisonBean;
import modzo.compare.ui.items.compare.ComparisonItemsService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Component
public class CommonDataMapper {
    private final ComparisonItemsService comparisonItemsService;

    private final ApplicationMessageSource messageSource;

    public CommonDataMapper(ComparisonItemsService comparisonItemsService, ApplicationMessageSource messageSource) {
        this.comparisonItemsService = comparisonItemsService;
        this.messageSource = messageSource;
    }

    public void addCommonData(Model model, Locale locale) {
        model.addAttribute("common.links.size.title", messageSource.getMessage("common.links.size.title", locale));
        model.addAttribute("common.comparisons.title", messageSource.getMessage("common.comparisons.title", locale));
        model.addAttribute("common.comparisons", comparisonItemsService.getItems(0, 50).getContent().stream()
                .map(bean -> new SeoComparisonBean(bean.getFirstItemName(), bean.getSecondItemName()))
                .collect(toList()));
        model.addAttribute("common.links.all.items", messageSource.getMessage("common.links.all.items", locale));
        model.addAttribute("common.links.all.items.title", messageSource.getMessage("common.links.all.items.title", locale));
        model.addAttribute("common.links.home", messageSource.getMessage("common.links.home", locale));
        model.addAttribute("common.links.home.title", messageSource.getMessage("common.links.home.title", locale));
        model.addAttribute("common.copyright", messageSource.getMessage("common.copyright", locale));
        model.addAttribute("common.links.site.url", messageSource.getMessage("common.links.site.url", locale));
        model.addAttribute("common.images.favicon", messageSource.getMessage("common.images.favicon", locale));
        model.addAttribute("common.images.logo", messageSource.getMessage("common.images.logo", locale));
        model.addAttribute("common.search.placeholder", messageSource.getMessage("common.search.placeholder", locale));
        model.addAttribute("common.facebook.page", messageSource.getMessage("common.facebook.page", locale));
    }
}
