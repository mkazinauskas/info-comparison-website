package modzo.compare.query.core.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface Items extends ElasticsearchRepository<Item, String> {
    Optional<Item> findByUniqueId(String uniqueId);

    Optional<Item> findByName(String name);
}
