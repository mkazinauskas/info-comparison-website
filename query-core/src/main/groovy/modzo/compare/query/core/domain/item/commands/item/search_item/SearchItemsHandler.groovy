package modzo.compare.query.core.domain.item.commands.item.search_item

import groovy.transform.CompileStatic
import modzo.compare.query.core.domain.item.Item
import modzo.compare.query.core.domain.item.Items
import org.elasticsearch.index.query.BoolQueryBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

import static org.elasticsearch.index.query.QueryBuilders.boolQuery
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery

@Component
@CompileStatic
class SearchItemsHandler {

    private final Items items

    private final SearchItemsValidator validator

    SearchItemsHandler(Items items, SearchItemsValidator validator) {
        this.items = items
        this.validator = validator
    }

    SearchItemsResult handle(SearchItems command) {
        validator.validate(command)

        BoolQueryBuilder builder =
                boolQuery()
                        .should(queryStringQuery(command.name).field('name'))
                        .should(queryStringQuery(command.description).field('description'))

        Pageable pageable = new PageRequest(command.page, command.size)
        Page<Item> searchResult = items.search(builder, pageable)

        return new SearchItemsResult(searchResult)
    }
}
