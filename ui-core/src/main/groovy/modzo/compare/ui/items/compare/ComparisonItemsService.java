package modzo.compare.ui.items.compare;

import modzo.compare.query.core.api.compare_items.ComparisonBean;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;

@Component
public class ComparisonItemsService {
    private final CompareItemsClient compareItemsClient;

    public ComparisonItemsService(CompareItemsClient compareItemsClient) {
        this.compareItemsClient = compareItemsClient;
    }

    public PagedResources<ComparisonBean> getItems(int page, int size) {
        return compareItemsClient.getComparisons(page, size).getBody();
    }
}
