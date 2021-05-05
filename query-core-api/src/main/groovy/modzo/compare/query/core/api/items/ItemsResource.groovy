package modzo.compare.query.core.api.items

import org.springframework.hateoas.PagedResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import static org.springframework.web.bind.annotation.RequestMethod.GET

interface ItemsResource {
    @RequestMapping(value = '/items', method = GET)
    ResponseEntity<PagedResources<ItemBean>> getItems(@RequestParam(value = 'page', defaultValue = '0') int page,
                                                      @RequestParam(value = 'size', defaultValue = '100') int size)

    @RequestMapping(value = '/items/{uniqueId}', method = GET)
    ResponseEntity<ItemBean> getItem(@PathVariable("uniqueId") String uniqueId)
}