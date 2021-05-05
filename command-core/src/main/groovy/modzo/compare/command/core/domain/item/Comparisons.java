package modzo.compare.command.core.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Comparisons extends JpaRepository<Comparison, Long> {
    Optional<Comparison> findByFirstItemUniqueIdAndSecondItemUniqueId(String firstItemUniqueId, String secondIteUniqueId);
}
