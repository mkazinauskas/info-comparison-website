package modzo.compare.ui

import modzo.compare.ui.items.ItemPhotosClient
import modzo.compare.ui.items.ItemPropertiesClient
import modzo.compare.ui.items.ItemsResourceClient
import modzo.compare.ui.items.ItemsSearchResourceClient
import modzo.compare.ui.items.compare.CompareItemsClient
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Configuration

@Configuration
class UiTestConfiguration {

    @MockBean
    CompareItemsClient compareItemsClient

    @MockBean
    ItemsResourceClient itemsResourceClient

    @MockBean
    ItemPropertiesClient itemPropertiesClient

    @MockBean
    ItemsSearchResourceClient itemsSearchResourceClient

    @MockBean
    ItemPhotosClient itemPhotosClient
}
