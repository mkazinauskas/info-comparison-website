package modzo.compare.command.core.domain

import modzo.compare.command.core.domain.item.commands.create.item.CreateItem
import modzo.compare.command.core.domain.item.commands.create.item.CreateItemHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class DummyItem {

    @Autowired
    private CreateItemHandler createItemHandler

    static CreateItem newItem() {
        return new CreateItem(
                name: randomAlphanumeric(30),
                description: randomAlphanumeric(10),
                identifier: randomAlphanumeric(10)
        )
    }

    String create(CreateItem command = newItem()) {
        createItemHandler.handle(command)
        return command.uniqueId
    }
}
