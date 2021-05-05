package modzo.compare.command.core.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Items extends JpaRepository<Item, Long> {

    Optional<Item> findByUniqueId(String uniqueId);

    Optional<Item> findByName(String name);

    Optional<Item> findByIdentifier(String identifier);
}
