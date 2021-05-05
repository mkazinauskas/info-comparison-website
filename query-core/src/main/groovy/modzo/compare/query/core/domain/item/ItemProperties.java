package modzo.compare.query.core.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface ItemProperties extends ElasticsearchRepository<ItemProperty, String> {

    Optional<ItemProperty> findByUniqueId(String uniqueId);

    List<ItemProperty> findByItemUniqueId(String itemUniqueId);
}
