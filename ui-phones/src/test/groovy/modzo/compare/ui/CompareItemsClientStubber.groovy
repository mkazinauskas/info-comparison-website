package modzo.compare.ui

import modzo.compare.query.core.api.compare_items.ComparisonBean
import modzo.compare.ui.items.compare.CompareItemsClient
import org.springframework.hateoas.PagedResources
import org.springframework.http.ResponseEntity

import static org.mockito.Mockito.when

class CompareItemsClientStubber {
    static stub(CompareItemsClient client) {
        when(client.getComparisons(0, 50))
                .thenReturn(
                ResponseEntity.ok(
                        new PagedResources(
                                [new ComparisonBean(
                                        uniqueId: 'uniqueId',
                                        firstItemUniqueId: 'firstItemUniqueId',
                                        firstItemName: 'firstItemName',
                                        secondItemUniqueId: 'secondItemUniqueId',
                                        secondItemName: 'secondItemName'
                                )],
                                new PagedResources.PageMetadata(1, 1, 1)
                        )
                )
        )
    }
}
