package modzo.compare.query.core.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PropertyDescriptors extends ElasticsearchRepository<PropertyDescriptor, String> {

    Optional<PropertyDescriptor> findByUniqueId(String uniqueId);

    Optional<PropertyDescriptor> findByParentPropertyDescriptorUniqueIdAndName(String parentPropertyDescriptorUniqueId, String name);
}
