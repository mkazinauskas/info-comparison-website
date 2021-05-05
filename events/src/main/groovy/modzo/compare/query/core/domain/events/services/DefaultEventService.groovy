package modzo.compare.query.core.domain.events.services

import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
@PackageScope
@Slf4j
class DefaultEventService implements EventService {

    private final EventServiceConfiguration configuration

    private final RestTemplate restTemplate

    DefaultEventService(EventServiceConfiguration configuration, RestTemplate restTemplate) {
        this.configuration = configuration
        this.restTemplate = restTemplate
    }

    @Override
    void publish(EventService.Event event) {
        log.info("Using default event service. Publishing event `${event}`")
        restTemplate.postForEntity("${configuration.eventStoreUrl}/events", event, ResponseEntity)
    }

    @Override
    Optional<EventService.RetrievedEvent> retrieveNext(Long currentEventId) {
        String url = "${configuration.eventStoreUrl}/events/next?id=$currentEventId"
        try {
            EventService.RetrievedEvent event = restTemplate
                    .getForEntity(url, EventService.RetrievedEvent
            ).getBody()
            return Optional.of(event)
        } catch (HttpClientErrorException clientError) {
            log.debug("Failed to retrieve event from url: `${url}`", clientError)
            return Optional.empty()
        } catch (Exception exception) {
            log.error("Failed to retrieve event from url: `${url}`", exception)
            return Optional.empty()
        }
    }
}
