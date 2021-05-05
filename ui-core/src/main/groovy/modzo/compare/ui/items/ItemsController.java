package modzo.compare.ui.items;

import modzo.compare.query.core.api.item_properties.ItemPropertyDescriptorBean;
import modzo.compare.query.core.api.items.ItemBean;
import modzo.compare.query.core.api.photos.ItemPhotoBean;
import modzo.compare.ui.ApplicationMessageSource;
import modzo.compare.ui.Pagination;
import modzo.compare.ui.common.CommonDataMapper;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Component
public class ItemsController {
    private final ItemsResourceClient itemsResourceClient;

    private final ItemPropertiesClient itemPropertiesClient;

    private final ItemsSearchResourceClient itemsSearchResourceClient;

    private final CommonDataMapper commonDataMapper;

    private final ApplicationMessageSource messageSource;

    private final PhotosService photosService;

    public ItemsController(ItemsResourceClient itemsResourceClient,
                           ItemPropertiesClient itemPropertiesClient,
                           ItemsSearchResourceClient itemsSearchResourceClient,
                           CommonDataMapper commonDataMapper,
                           ApplicationMessageSource messageSource,
                           PhotosService photosService) {
        this.itemsResourceClient = itemsResourceClient;
        this.itemPropertiesClient = itemPropertiesClient;
        this.itemsSearchResourceClient = itemsSearchResourceClient;
        this.commonDataMapper = commonDataMapper;
        this.messageSource = messageSource;
        this.photosService = photosService;
    }

    String getItems(int page, int size, String query, Model model, Locale locale) {
        PagedResources<ItemBean> items;
        String finalQuery = null;
        if (isNotBlank(query)) {
            finalQuery = query.replaceAll("-", " ");
            items = itemsSearchResourceClient.searchItems(finalQuery, page, size).getBody();
        } else {
            items = itemsResourceClient.getItems(page, size).getBody();
        }
        Collection<ItemBean> content = items.getContent();

        List<Pair<ItemBean, ItemPhotoBean>> collectedItems = content.stream()
                .map(item -> new Pair<>(item, photosService.itemPhoto(item.getUniqueId())))
                .collect(toList());

        model.addAttribute("items", collectedItems);
        model.addAttribute("query", finalQuery);
        model.addAttribute("hasItems", !items.getContent().isEmpty());
        model.addAttribute("pagination", new Pagination(items.getMetadata()));

        commonDataMapper.addCommonData(model, locale);

        if (isBlank(finalQuery)) {
            model.addAttribute("page.title",
                    messageSource.getMessage("page.items.title.default", locale));
            model.addAttribute("page.description",
                    messageSource.getMessage("page.items.description.default", locale));
        } else {
            model.addAttribute("page.title",
                    messageSource.getMessage("page.items.title.search", locale, finalQuery));
            model.addAttribute("page.description",
                    messageSource.getMessage("page.items.description.search", locale, finalQuery));
        }

        model.addAttribute("page.items.h1",
                messageSource.getMessage("page.items.h1", locale));

        model.addAttribute("page.items.found.h1",
                messageSource.getMessage("page.items.found.h1", locale));

        model.addAttribute("page.items.not.found",
                messageSource.getMessage("page.items.not.found", locale));

        model.addAttribute("page.items.item.title",
                messageSource.getMessage("page.items.item.title", locale));

        model.addAttribute("page.items.item.description",
                messageSource.getMessage("page.items.item.description", locale));

        return "items/items";
    }

    String getItem(String uniqueId, Model model, Locale locale) {
        ItemBean item = itemsResourceClient.getItem(uniqueId).getBody();
        Collection<ItemPropertyDescriptorBean> properties = itemPropertiesClient.getItemProperties(uniqueId).getBody().getContent();
        model.addAttribute("item", item);
        model.addAttribute("itemPhotos", photosService.itemPhotos(item.getUniqueId()));
        model.addAttribute("properties", properties);

        commonDataMapper.addCommonData(model, locale);

        model.addAttribute("page.title",
                messageSource.getMessage("page.item.title", locale, item.getName()));
        model.addAttribute("page.description",
                messageSource.getMessage("page.item.description", locale, item.getName()));

        model.addAttribute("page.item.h1.data",
                messageSource.getMessage("page.item.h1.data", locale, item.getName()));

        model.addAttribute("page.item.h1.photos",
                messageSource.getMessage("page.item.h1.photos", locale, item.getName()));

        model.addAttribute("page.item.back",
                messageSource.getMessage("page.item.back", locale));

        return "items/item";
    }


}
