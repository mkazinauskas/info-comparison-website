package modzo.compare.query.core.api.items;

import modzo.compare.query.core.api.ApiException;
import modzo.compare.query.core.domain.item.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;

@RestController
class ItemsController implements ItemsResource {

    private final Items items;

    private final ItemBeanMapper itemBeanMapper;

    public ItemsController(Items items, ItemBeanMapper itemBeanMapper) {
        this.items = items;
        this.itemBeanMapper = itemBeanMapper;
    }

    @Override
    public ResponseEntity<PagedResources<ItemBean>> getItems(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "order"));
        Page<ItemBean> results = items.findAll(pageable).map(itemBeanMapper::map);
        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(
                results.getSize(),
                results.getNumber(),
                results.getTotalElements(),
                results.getTotalPages()
        );
        return ok(new PagedResources<>(results.getContent(), pageMetadata));
    }

    @Override
    public ResponseEntity<ItemBean> getItem(@PathVariable("uniqueId") String uniqueId) {
        return ok(items.findByUniqueId(uniqueId).map(itemBeanMapper::map).orElseThrow(
                () -> new ApiException("UNIQUE_ID_IS_NOT_FOUND", "Item with uniqueId is not found", NOT_FOUND)
        ));
    }
}
