package modzo.compare.query.core.domain.parser

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = 'parsed_event', type = 'parsed_event', refreshInterval = '-1')
class ParsedEvent {
    @Id
    Long id
}
