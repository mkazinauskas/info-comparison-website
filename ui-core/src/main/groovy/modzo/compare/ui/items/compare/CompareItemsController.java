package modzo.compare.ui.items.compare;

import modzo.compare.query.core.api.compare_items.CompareItemsBean;
import modzo.compare.query.core.api.items.ItemBean;
import modzo.compare.ui.ApplicationMessageSource;
import modzo.compare.ui.common.CommonDataMapper;
import modzo.compare.ui.items.HostTool;
import modzo.compare.ui.items.ItemsSearchResourceClient;
import modzo.compare.ui.items.PhotosService;
import modzo.compare.ui.items.SeoTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Locale;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Controller
public class CompareItemsController {
    private final ItemsSearchResourceClient itemsSearchResourceClient;

    private final CompareItemsClient compareItemsClient;

    private final CommonDataMapper commonDataMapper;

    private final ApplicationMessageSource messageSource;

    private final HostTool hostTool;

    private final PhotosService photosService;

    public CompareItemsController(ItemsSearchResourceClient itemsSearchResourceClient,
                                  CompareItemsClient compareItemsClient,
                                  CommonDataMapper commonDataMapper,
                                  ApplicationMessageSource messageSource,
                                  HostTool hostTool,
                                  PhotosService photosService) {
        this.itemsSearchResourceClient = itemsSearchResourceClient;
        this.compareItemsClient = compareItemsClient;
        this.commonDataMapper = commonDataMapper;
        this.messageSource = messageSource;
        this.hostTool = hostTool;
        this.photosService = photosService;
    }

    @PostMapping(value = "/compare")
    ModelAndView compareItems(@ModelAttribute("form") CompareRequest compareRequest, Locale locale) {
        String firstItem = SeoTool.make(compareRequest.getFirstItem());
        String secondItem = SeoTool.make(compareRequest.getSecondItem());
        if (isNotBlank(firstItem) && isNotBlank(secondItem)) {
            return new ModelAndView(
                    hostTool.getRedirectLink(format("/%s-versus-%s", firstItem, secondItem))
            );
        }
        String itemsLink = messageSource.getMessage("common.links.all.items", locale);
        if (isNotBlank(firstItem)) {
            return new ModelAndView(
                    hostTool.getRedirectLink(format(itemsLink + "?query=%s", firstItem))
            );
        }

        if (isNotBlank(secondItem)) {
            return new ModelAndView(
                    hostTool.getRedirectLink(format(itemsLink + "?query=%s", secondItem))
            );
        }

        return new ModelAndView(hostTool.getRedirectLink("/"));
    }

    @GetMapping("/{firstItem}-versus-{secondItem}")
    String compareItems(@PathVariable("firstItem") String firstItem,
                        @PathVariable("secondItem") String secondItem,
                        Model model,
                        Locale locale) {

        firstItem = firstItem.replaceAll("-", " ");
        secondItem = secondItem.replaceAll("-", " ");
        Collection<ItemBean> foundFirstItems = itemsSearchResourceClient
                .searchItems(firstItem, 0, 1).getBody().getContent();

        Collection<ItemBean> foundSecondItems = itemsSearchResourceClient
                .searchItems(secondItem, 0, 1).getBody().getContent();
        if (!foundFirstItems.isEmpty() && !foundSecondItems.isEmpty()) {
            String firstItemUniqueId = foundFirstItems.iterator().next().getUniqueId();
            String secondItemUniqueId = foundSecondItems.iterator().next().getUniqueId();
            CompareItemsBean compareItemsBeanResponseEntity = compareItemsClient.compareItems(
                    firstItemUniqueId,
                    secondItemUniqueId
            ).getBody();

            model.addAttribute("compareItems", compareItemsBeanResponseEntity);
            commonDataMapper.addCommonData(model, locale);

            model.addAttribute("page.title",
                    messageSource.getMessage("page.compare.title", locale, firstItem, secondItem));
            model.addAttribute("page.description",
                    messageSource.getMessage("page.compare.description", locale, firstItem, secondItem));

            model.addAttribute("page.compare.h1.data",
                    messageSource.getMessage("page.compare.h1.data", locale, firstItem, secondItem));

            model.addAttribute("page.compare.h1.photos",
                    messageSource.getMessage("page.compare.h1.photos", locale, firstItem, secondItem));

            model.addAttribute("page.compare.back", messageSource.getMessage("page.compare.back", locale));

            model.addAttribute("page.path",
                    firstItem.replaceAll(" ", "-")
                            + "-versus-" + secondItem.replaceAll(" ", "-"));

            model.addAttribute("firstItemPhotos", photosService.itemPhotos(firstItemUniqueId));
            model.addAttribute("secondItemPhotos", photosService.itemPhotos(secondItemUniqueId));
            return "compare/main";
        }
        String itemsLink = messageSource.getMessage("common.links.all.items", locale);
        if (!foundFirstItems.isEmpty()) {
            return hostTool.getRedirectLink(format(itemsLink + "?query=%s", firstItem));
        }
        if (!foundSecondItems.isEmpty()) {
            return hostTool.getRedirectLink(format(itemsLink + "?query=%s", secondItem));
        }
        return hostTool.getRedirectLink("/");
    }
}
