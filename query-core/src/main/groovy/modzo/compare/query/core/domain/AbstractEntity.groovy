package modzo.compare.query.core.domain

import org.springframework.data.annotation.Id

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

abstract class AbstractEntity {
    @Id
    String uniqueId

    void randomUniqueId() {
        uniqueId = randomAlphanumeric(40)
    }
}
