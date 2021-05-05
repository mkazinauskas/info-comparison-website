package modzo.compare.ui.items;

import modzo.compare.query.core.api.items.ItemBean;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuggestItemController {
    private final ItemsSearchResourceClient itemsSearchResourceClient;

    public SuggestItemController(ItemsSearchResourceClient itemsSearchResourceClient) {
        this.itemsSearchResourceClient = itemsSearchResourceClient;
    }

    @GetMapping(value = "/suggest", produces = MediaType.APPLICATION_JSON_VALUE, params = {"name"})
    ResponseEntity<PagedResources<ItemBean>> getSuggestion(@RequestParam(value = "name") String name) {
        return itemsSearchResourceClient.searchItems(name, 0, 10);
    }
}
