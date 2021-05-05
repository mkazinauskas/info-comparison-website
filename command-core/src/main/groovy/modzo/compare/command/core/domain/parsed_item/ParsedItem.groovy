package modzo.compare.command.core.domain.parsed_item

import modzo.compare.command.core.domain.AbstractEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

import static java.util.Optional.ofNullable
import static javax.persistence.EnumType.STRING

@Entity
@Table(name = 'parsed_items')
class ParsedItem extends AbstractEntity {

    @Column(name = 'item_unique_id', nullable = true)
    String itemUniqueId

    @Column(name = 'identifier', nullable = false)
    String identifier

    @Column(name = 'status', nullable = false)
    @Enumerated(value = STRING)
    Status status

    static enum Status {
        SUCCESS, FAILED
    }

    Optional<String> getItemUniqueId() {
        ofNullable(itemUniqueId)
    }
}
