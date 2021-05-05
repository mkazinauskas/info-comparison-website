package modzo.compare.query.core.domain.events.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Preconditions
import modzo.compare.query.core.domain.events.Event
import org.springframework.stereotype.Component

@Component
class EventPublisher {

    private final ObjectMapper objectMapper

    private final EventService eventService

    EventPublisher(ObjectMapper objectMapper, EventService eventService) {
        this.objectMapper = objectMapper
        this.eventService = eventService
    }

    void publish(Event event) {
        Preconditions.checkNotNull(event, 'Event cannot be null')
        Preconditions.checkNotNull(event.topic(), 'Event topic cannot be null')

        String serializedEvent = objectMapper.writeValueAsString(event)
        eventService.publish(new EventService.Event(
                uniqueId: event.uniqueId(),
                type: event.topic(),
                data: serializedEvent
        ))
    }
}
