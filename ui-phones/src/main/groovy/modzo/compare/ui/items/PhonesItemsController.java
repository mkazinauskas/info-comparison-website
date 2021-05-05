package modzo.compare.ui.items;

import modzo.compare.ui.ApplicationMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class PhonesItemsController {

    private final ItemsController itemsController;

    private final ApplicationMessageSource messageSource;

    public PhonesItemsController(ItemsController itemsController, ApplicationMessageSource messageSource) {
        this.itemsController = itemsController;
        this.messageSource = messageSource;
    }

    @GetMapping("/phones")
    String getItems(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                    @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                    @RequestParam(value = "query", required = false) String query,
                    Model model, Locale locale) {
        model.addAttribute("page.path", "/phones?page="
                + page + "&size=" + size + "&query=" + query);
        return itemsController.getItems(page, size, query, model, locale);
    }

    @GetMapping("/phones/{name}-{uniqueId}")
    String getItem(@PathVariable("uniqueId") String uniqueId,
                   @PathVariable("name") String name, Model model, Locale locale) {
        model.addAttribute("page.path", "/phones?" + name + "-" + uniqueId);
        return itemsController.getItem(uniqueId, model, locale);
    }
}
