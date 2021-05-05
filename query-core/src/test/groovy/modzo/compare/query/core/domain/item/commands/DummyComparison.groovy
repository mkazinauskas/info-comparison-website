package modzo.compare.query.core.domain.item.commands

import modzo.compare.query.core.domain.item.commands.comparison.CreateComparison
import modzo.compare.query.core.domain.item.commands.comparison.CreateComparisonHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@Component
class DummyComparison {
    @Autowired
    private CreateComparisonHandler handler

    @Autowired
    private DummyItem dummyItem

    String create(String firstItemUniqueId = dummyItem.create(),
                  String secondItemUniqueId = dummyItem.create()
    ) {
        CreateComparison createComparison = new CreateComparison(
                uniqueId: randomAlphanumeric(30),
                firstItemUniqueId: firstItemUniqueId,
                secondItemUniqueId: secondItemUniqueId
        )
        handler.handle(createComparison)

        return createComparison.uniqueId
    }
}
