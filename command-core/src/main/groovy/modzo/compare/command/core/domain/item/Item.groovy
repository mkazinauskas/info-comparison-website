package modzo.compare.command.core.domain.item

import modzo.compare.command.core.domain.AbstractEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'items')
class Item extends AbstractEntity {

    @Column(name = 'identifier', nullable = false)
    String identifier

    @Column(name = 'name', nullable = false)
    String name

    @Column(name = 'description')
    String description
}
