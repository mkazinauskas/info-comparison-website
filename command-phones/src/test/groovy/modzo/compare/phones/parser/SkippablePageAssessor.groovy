package modzo.compare.phones.parser

import modzo.compare.command.core.domain.parsed_item.ParsedItem
import modzo.compare.command.core.domain.parsed_item.ParsedItems
import modzo.compare.phones.PhonesParserConfiguration
import spock.lang.Specification

class SkippablePageAssessor extends Specification {
    ParsedItems parsedItems = Stub()

    PhonesParserConfiguration phonesParserConfiguration = Stub()

    SkipablePageAssessor isSkipablePage = new SkipablePageAssessor(parsedItems, phonesParserConfiguration)

    def 'should skip page when success status exists'() {
        given:
            doNotRetryOnFailedItems()
        and:
            successParsedItemExists()
        expect:
            isSkipablePage.isSkippable(1)
    }

    def 'should not skip page when failed status exists and retry is enabled on failed items'() {
        given:
            retryOnFailedItems()
        and:
            failedParsedItemExists()
        expect:
            !isSkipablePage.isSkippable(1)
    }

    def 'should not skip page when failed status exists and retry is not enabled on failed items'() {
        given:
            doNotRetryOnFailedItems()
        and:
            failedParsedItemExists()
        expect:
            isSkipablePage.isSkippable(1)
    }

    private void retryOnFailedItems() {
        phonesParserConfiguration.gsmArenaFetcher >> new PhonesParserConfiguration.GsmArenaFetcherConfiguration(retryOfFailed: true)
    }

    private void doNotRetryOnFailedItems() {
        phonesParserConfiguration.gsmArenaFetcher >> new PhonesParserConfiguration.GsmArenaFetcherConfiguration(retryOfFailed: false)
    }

    private void failedParsedItemExists() {
        parsedItems.findByIdentifier('1') >> [new ParsedItem(status: ParsedItem.Status.FAILED)]
    }

    private void successParsedItemExists() {
        parsedItems.findByIdentifier('1') >> [new ParsedItem(status: ParsedItem.Status.SUCCESS)]
    }
}
