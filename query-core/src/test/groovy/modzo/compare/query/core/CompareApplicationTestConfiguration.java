package modzo.compare.query.core;

import modzo.compare.query.core.api.compare_items.ComparisonCommandClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompareApplicationTestConfiguration {

    @MockBean
    ComparisonCommandClient commandClient;
}
