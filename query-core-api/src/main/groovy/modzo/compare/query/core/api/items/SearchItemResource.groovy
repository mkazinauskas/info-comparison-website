package modzo.compare.query.core.api.items

import org.springframework.hateoas.PagedResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import static org.springframework.web.bind.annotation.RequestMethod.GET

interface SearchItemResource {
    @RequestMapping(value = '/items/search', method = GET, params = ['query', 'page', 'size'])
    ResponseEntity<PagedResources<ItemBean>> searchItems(@RequestParam('query') String query,
                                                         @RequestParam(value = 'page', defaultValue = '0') int page,
                                                         @RequestParam(value = 'size', defaultValue = '20') int size)

}
