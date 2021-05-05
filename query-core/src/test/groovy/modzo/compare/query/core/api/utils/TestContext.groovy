package modzo.compare.query.core.api.utils

import modzo.compare.query.core.api.compare_items.CompareItemsBean
import modzo.compare.query.core.api.compare_items.ComparisonBean
import modzo.compare.query.core.domain.item.commands.item.CreateItem
import modzo.compare.query.core.domain.item.commands.item.CreateItemHandler
import org.apache.commons.lang.RandomStringUtils
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class TestContext {

    private final RequestTemplate requestTemplate

    private final CreateItemHandler createItemHandler

    TestContext(RequestTemplate requestTemplate, CreateItemHandler createItemHandler) {
        this.requestTemplate = requestTemplate
        this.createItemHandler = createItemHandler
    }

    ResponseEntity<String> retrieveItems() {
        return requestTemplate.get('/items')
    }

    ResponseEntity<CompareItemsBean> compareItems(String firstItem, String secondItem) {
        return requestTemplate.getForEntity(
                "/compare?firstItemUniqueId=${firstItem}&secondItemUniqueId=${secondItem}",
                CompareItemsBean
        )
    }

    ResponseEntity<String> getComparisons(int page, int size) {
        return requestTemplate.getForEntity(
                "/comparisons?page=${page}&size=${size}",
                String
        )
    }

    ResponseEntity<String> retrieveItem(String uniqueId) {
        return requestTemplate.get("/items/${uniqueId}")
    }

    ResponseEntity<String> searchItems(String query, int page = 0, int size = 20) {
        return requestTemplate.get("/items/search?query=${query}&page=${page}&size=${size}")
    }

    CreateItem item() {
        new CreateItem(
                uniqueId: RandomStringUtils.randomAlphanumeric(40),
                name: RandomStringUtils.randomAlphanumeric(50),
                description: RandomStringUtils.randomAlphanumeric(60)
        )
    }

    String createItem(CreateItem request = item()) {
        createItemHandler.handle(request)
        return request.uniqueId
    }
}
