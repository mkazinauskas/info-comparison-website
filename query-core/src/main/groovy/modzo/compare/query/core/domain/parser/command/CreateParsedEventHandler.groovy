package modzo.compare.query.core.domain.parser.command

import modzo.compare.query.core.domain.parser.ParsedEvent
import modzo.compare.query.core.domain.parser.ParsedEvents
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateParsedEventHandler {

    private final CreateParsedEventValidator validator
    private final ParsedEvents parsedItems

    CreateParsedEventHandler(CreateParsedEventValidator validator, ParsedEvents parsedItems) {
        this.validator = validator
        this.parsedItems = parsedItems
    }

    void handle(CreateParsedEvent command) {
        validator.validate(command)

        parsedItems.save(new ParsedEvent(
                id: command.id
        ))
    }
}
