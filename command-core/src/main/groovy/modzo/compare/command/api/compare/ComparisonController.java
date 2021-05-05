package modzo.compare.command.api.compare;

import modzo.compare.command.core.domain.item.commands.create.comparison.CreateComparison;
import modzo.compare.command.core.domain.item.commands.create.comparison.CreateComparisonHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ComparisonController implements ComparisonResource {

    private final CreateComparisonHandler createComparisonHandler;

    public ComparisonController(CreateComparisonHandler createComparisonHandler) {
        this.createComparisonHandler = createComparisonHandler;
    }

    @Override
    public ResponseEntity createComparison(@RequestBody ComparisonRequest request) {
        createComparisonHandler.handle(
                new CreateComparison(request.getFirstItemUniqueId(), request.getSecondItemUniqueId())
        );
        return ResponseEntity.created(URI.create("/comparison")).build();
    }

    @Override
    public ResponseEntity<ComparisonBean> retrieveComparison(String comparisonUniqueId) {
        return ResponseEntity.notFound().build();
    }
}
