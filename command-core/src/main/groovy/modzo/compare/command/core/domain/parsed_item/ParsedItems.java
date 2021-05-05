package modzo.compare.command.core.domain.parsed_item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParsedItems extends JpaRepository<ParsedItem, Long> {

    List<ParsedItem> findByIdentifier(String identifier);

    Optional<ParsedItem> findByUniqueId(String uniqueId);

}
