package modzo.compare.command.core.events

import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import modzo.compare.query.core.domain.events.services.EventService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@PackageScope
@Primary
@Slf4j
@ConditionalOnProperty(name = 'application.mock.event.service', havingValue = 'true')
class MockEventService implements EventService {

    private final RestTemplate restTemplate

    MockEventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    @Override
    void publish(EventService.Event event) {
        log.info("Using mock event service. Publishing event `${event}`")
    }

    @Override
    Optional<EventService.RetrievedEvent> retrieveNext(Long currentEventId) {
        log.info("Using mock event service. Retrieving next `${currentEventId}`")
        return Optional.empty()
    }
}
