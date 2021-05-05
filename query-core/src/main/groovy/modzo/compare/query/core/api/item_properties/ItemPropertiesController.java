package modzo.compare.query.core.api.item_properties;

import modzo.compare.query.core.api.item_properties.service.ItemPropertiesService;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class ItemPropertiesController implements ItemPropertiesResource {
    private final ItemPropertiesService itemPropertiesService;

    public ItemPropertiesController(ItemPropertiesService itemPropertiesService) {
        this.itemPropertiesService = itemPropertiesService;
    }

    @Override
    public ResponseEntity<Resources<ItemPropertyDescriptorBean>> getItemProperties(
            @PathVariable("uniqueId") String itemUniqueId) {
        return ok(new Resources<>(itemPropertiesService.getItemProperties(itemUniqueId)));
    }
}
