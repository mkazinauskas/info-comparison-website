package modzo.compare.phones.parser

import modzo.compare.command.core.domain.parsed_item.ParsedItems
import modzo.compare.phones.PhonesParserConfiguration
import org.springframework.stereotype.Component

import static modzo.compare.command.core.domain.parsed_item.ParsedItem.Status.FAILED
import static modzo.compare.command.core.domain.parsed_item.ParsedItem.Status.SUCCESS

@Component
class SkipablePageAssessor {

    private final ParsedItems parsedItems

    private final PhonesParserConfiguration phonesParserConfiguration

    SkipablePageAssessor(ParsedItems parsedItems, PhonesParserConfiguration phonesParserConfiguration) {
        this.parsedItems = parsedItems
        this.phonesParserConfiguration = phonesParserConfiguration
    }

    boolean isSkippable(int page) {
        parsedItems.findByIdentifier(page as String).stream()
                .filter { item ->
            (!phonesParserConfiguration.gsmArenaFetcher.retryOfFailed && FAILED == item.status) ||
                    SUCCESS == item.status
        }.findAny().isPresent()
    }
}
