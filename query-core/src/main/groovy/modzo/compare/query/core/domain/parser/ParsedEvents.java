package modzo.compare.query.core.domain.parser;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParsedEvents extends ElasticsearchRepository<ParsedEvent, Long> {
}
