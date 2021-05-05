package modzo.compare.query.core.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Comparisons extends ElasticsearchRepository<Comparison, String> {
    Optional<Comparison> findByUniqueId(String uniqueId);
}
