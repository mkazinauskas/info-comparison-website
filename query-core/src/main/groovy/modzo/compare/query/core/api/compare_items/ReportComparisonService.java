package modzo.compare.query.core.api.compare_items;

import modzo.compare.command.api.compare.ComparisonRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReportComparisonService {

    private final ComparisonCommandClient comparisonCommandClient;

    public ReportComparisonService(ComparisonCommandClient comparisonCommandClient) {
        this.comparisonCommandClient = comparisonCommandClient;
    }

    @Async
    public void report(String firstItemUniqueId, String secondItemUniqueId) {
        comparisonCommandClient.createComparison(
                new ComparisonRequest(firstItemUniqueId, secondItemUniqueId)
        );
    }
}
