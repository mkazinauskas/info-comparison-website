package modzo.compare.command.core.domain.parsed_item.command

import groovy.transform.CompileStatic
import modzo.compare.command.core.domain.parsed_item.ParsedItem

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic

@CompileStatic
class CreateParsedItem {
    String uniqueId = randomAlphabetic(40)

    Optional<String> itemUniqueId

    String identifier

    ParsedItem.Status status
}
