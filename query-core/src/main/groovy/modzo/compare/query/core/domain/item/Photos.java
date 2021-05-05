package modzo.compare.query.core.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface Photos extends ElasticsearchRepository<Photo, String> {

    Optional<Photo> findByUniqueId (String uniqueId);

    List<Photo> findByItemUniqueId (String itemUniqueId);
}
