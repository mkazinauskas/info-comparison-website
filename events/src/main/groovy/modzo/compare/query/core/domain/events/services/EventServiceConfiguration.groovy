package modzo.compare.query.core.domain.events.services

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(value = 'application.events')
class EventServiceConfiguration {
    String eventStoreUrl = 'http://localhost:8889'
}
