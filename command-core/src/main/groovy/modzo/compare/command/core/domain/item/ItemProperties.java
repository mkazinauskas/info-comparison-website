package modzo.compare.command.core.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemProperties extends JpaRepository<ItemProperty, Long> {

    Optional<ItemProperty> findByUniqueId(String uniqueId);

    Optional<ItemProperty> findByPropertyDescriptorUniqueIdAndValue(String propertyDescriptorUniqueId, String value);

    List<ItemProperty> findByItemUniqueId(String itemUniqueId);
}
