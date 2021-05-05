package modzo.compare.ui.comparisons;

import modzo.compare.query.core.api.compare_items.ComparisonBean;
import modzo.compare.ui.ApplicationMessageSource;
import modzo.compare.ui.Pagination;
import modzo.compare.ui.common.CommonDataMapper;
import modzo.compare.ui.items.SeoComparisonBean;
import modzo.compare.ui.items.compare.ComparisonItemsService;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Controller
public class ComparisonsController {

    private final CommonDataMapper commonDataMapper;

    private final ApplicationMessageSource messageSource;

    private final ComparisonItemsService comparisonItemsService;

    public ComparisonsController(CommonDataMapper commonDataMapper,
                                 ApplicationMessageSource messageSource,
                                 ComparisonItemsService comparisonItemsService) {
        this.commonDataMapper = commonDataMapper;
        this.messageSource = messageSource;
        this.comparisonItemsService = comparisonItemsService;
    }

    @GetMapping("/comparisons")
    String comparisons(Model model, Locale locale,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        commonDataMapper.addCommonData(model, locale);

        model.addAttribute("page.title",
                messageSource.getMessage("page.comparisons.title", locale));
        model.addAttribute("page.description",
                messageSource.getMessage("page.comparisons.description", locale));

        model.addAttribute("page.comparisons.h1",
                messageSource.getMessage("page.comparisons.h1", locale));
        model.addAttribute("page.path", "/comparisons");

        PagedResources<ComparisonBean> items = comparisonItemsService.getItems(page, size);
        model.addAttribute("data.comparisons", items.getContent().stream()
                .map(bean -> new SeoComparisonBean(bean.getFirstItemName(), bean.getSecondItemName()))
                .collect(toList()));

        model.addAttribute("data.hasItems", !items.getContent().isEmpty());
        model.addAttribute("pagination", new Pagination(items.getMetadata()));

        return "comparisons/comparisons";
    }

}
