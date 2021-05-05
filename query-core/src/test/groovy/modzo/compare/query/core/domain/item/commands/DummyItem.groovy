package modzo.compare.query.core.domain.item.commands

import modzo.compare.query.core.domain.item.commands.item.CreateItem
import modzo.compare.query.core.domain.item.commands.item.CreateItemHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@Component
class DummyItem {
    @Autowired
    private CreateItemHandler handler

    String create(String name = randomAlphanumeric(10),
                  String description = randomAlphanumeric(10)
    ) {
        CreateItem createItem = new CreateItem(
                uniqueId: randomAlphanumeric(30),
                name: name,
                description: description
        )
        handler.handle(createItem)

        return createItem.uniqueId
    }
}
