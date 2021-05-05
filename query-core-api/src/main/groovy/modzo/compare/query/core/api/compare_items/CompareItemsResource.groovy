package modzo.compare.query.core.api.compare_items

import org.springframework.hateoas.PagedResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import static org.springframework.web.bind.annotation.RequestMethod.GET

interface CompareItemsResource {
    @RequestMapping(value = '/compare', method = GET)
    ResponseEntity<CompareItemsBean> compareItems(@RequestParam("firstItemUniqueId") String firstItemUniqueId,
                                                  @RequestParam("secondItemUniqueId") String secondItemUniqueId)

    @RequestMapping(value = '/comparisons', method = GET)
    ResponseEntity<PagedResources<ComparisonBean>> getComparisons(
            @RequestParam(value = 'page', defaultValue = '0') int page,
            @RequestParam(value = 'size', defaultValue = '100') int size)

}