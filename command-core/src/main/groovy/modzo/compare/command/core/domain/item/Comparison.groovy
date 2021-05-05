package modzo.compare.command.core.domain.item

import modzo.compare.command.core.domain.AbstractEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'comparisons')
class Comparison extends AbstractEntity {

    @Column(name = 'first_item_unique_id', nullable = false)
    String firstItemUniqueId

    @Column(name = 'second_item_unique_id', nullable = false)
    String secondItemUniqueId

    @Column(name = 'sequence', nullable = false)
    int sequence
}
