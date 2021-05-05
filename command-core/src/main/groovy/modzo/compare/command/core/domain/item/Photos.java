package modzo.compare.command.core.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Photos extends JpaRepository<Photo, Long> {
    Optional<Photo> findByUniqueId(String uniqueId);

    List<Photo> findByItemUniqueId(String itemUniqueId);
}
