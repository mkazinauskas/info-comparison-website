package modzo.compare.phones.parser

import groovy.util.logging.Slf4j
import modzo.compare.command.core.domain.item.services.SaveBundleService
import modzo.compare.command.core.domain.parsed_item.ParsedItem
import modzo.compare.command.core.domain.parsed_item.command.CreateParsedItem
import modzo.compare.command.core.domain.parsed_item.command.CreateParsedItemHandler
import org.springframework.stereotype.Component

import static java.util.Optional.empty
import static java.util.Optional.of
import static modzo.compare.phones.parser.PhoneDetailsParser.parse

@Component
@Slf4j
class ParserExecutor {

    private final GsmArenaFetcher gsmArenaFetcher

    private final CreateParsedItemHandler createParsedItemHandler

    private final SkipablePageAssessor isSkipablePage

    private final SaveBundleService saveBundleService

    ParserExecutor(GsmArenaFetcher gsmArenaFetcher,
                   CreateParsedItemHandler createParsedItemHandler,
                   SkipablePageAssessor isSkipablePage,
                   SaveBundleService saveBundleService) {
        this.gsmArenaFetcher = gsmArenaFetcher
        this.createParsedItemHandler = createParsedItemHandler
        this.isSkipablePage = isSkipablePage
        this.saveBundleService = saveBundleService
    }

    void parsePage(int page) {
        try {
            if (isSkipablePage.isSkippable(page)) {
                return
            }

            Optional<String> fetchedPage = gsmArenaFetcher.fetch(page)
            if (fetchedPage.isPresent()) {
                parseAndPersist(fetchedPage.get(), page as String)
            } else {
                saveStatus(page as String, ParsedItem.Status.FAILED, empty())
            }
        } catch (Exception ex) {
            log.error("Parsing page `$page` has failed", ex)
            saveStatus(page as String, ParsedItem.Status.FAILED, empty())
        }

    }

   private void parseAndPersist(String content, String page) {
        SaveBundleService.Bundle bundle = parse(content)
        bundle.identifier = page

        String itemUniqueId = saveBundleService.save(bundle)

        saveStatus(page, ParsedItem.Status.SUCCESS, of(itemUniqueId))
    }

    private void saveStatus(String page, ParsedItem.Status status, Optional<String> itemUniqueId) {
        createParsedItemHandler.handle(new CreateParsedItem(
                itemUniqueId: itemUniqueId,
                identifier: page,
                status: status
        ))
    }
}
