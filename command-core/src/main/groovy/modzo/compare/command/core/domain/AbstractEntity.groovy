package modzo.compare.command.core.domain

import javax.persistence.*

import static javax.persistence.GenerationType.IDENTITY
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@MappedSuperclass
abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = 'id', nullable = false)
    Long id

    @Column(name = 'unique_id', nullable = false)
    String uniqueId

    @Column(name = 'created', nullable = false)
    Date created = new Date()

    @Version
    @Column(name = 'version', nullable = false)
    Long version

    void randomUniqueId() {
        uniqueId = randomAlphanumeric(40)
    }
}
