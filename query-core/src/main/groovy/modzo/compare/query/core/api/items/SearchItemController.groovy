package modzo.compare.query.core.api.items

import groovy.transform.CompileStatic
import modzo.compare.query.core.domain.item.Item
import modzo.compare.query.core.domain.item.commands.item.search_item.SearchItems
import modzo.compare.query.core.domain.item.commands.item.search_item.SearchItemsHandler
import org.springframework.data.domain.Page
import org.springframework.hateoas.PagedResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.ResponseEntity.ok

@RestController
@CompileStatic
class SearchItemController implements SearchItemResource {

    private SearchItemsHandler searchItemsHandler

    private ItemBeanMapper itemBeanMapper

    SearchItemController(SearchItemsHandler searchItemsHandler, ItemBeanMapper itemBeanMapper) {
        this.searchItemsHandler = searchItemsHandler
        this.itemBeanMapper = itemBeanMapper
    }

    @Override
    ResponseEntity<PagedResources<ItemBean>> searchItems(
            @RequestParam('query') String query,
            @RequestParam(value = 'page', defaultValue = '0') int page,
            @RequestParam(value = 'size', defaultValue = '20') int size) {

        Page<Item> searchResult = searchItemsHandler.handle(new SearchItems(query, query, page, size)).items

        List<ItemBean> itemBeans = searchResult.content.collect { itemBeanMapper.map(it) }

        PagedResources.PageMetadata metadata = new PagedResources.PageMetadata(
                searchResult.size,
                searchResult.number,
                searchResult.totalElements,
                searchResult.totalPages
        )
        return ok(new PagedResources<ItemBean>(itemBeans, metadata))
    }
}
