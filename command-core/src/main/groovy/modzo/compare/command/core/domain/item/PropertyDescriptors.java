package modzo.compare.command.core.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyDescriptors extends JpaRepository<PropertyDescriptor, Long> {

    Optional<PropertyDescriptor> findByUniqueId(String uniqueId);

    Optional<PropertyDescriptor> findByName(String name);

    Optional<PropertyDescriptor> findByParentPropertyDescriptorUniqueIdAndName(String parentPropertyDescriptorUniqueId, String name);
}
