package modzo.compare.query.core.domain.events.services

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import modzo.compare.query.core.domain.events.Event
import modzo.compare.query.core.domain.events.services.EventService
import org.springframework.stereotype.Component

@Component
@CompileStatic
class EventDeserializer {

    private final ObjectMapper objectMapper

    EventDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
    }

    def <T extends Event> T deserialize(EventService.RetrievedEvent retrievedEvent, Class<T> clazz) {
        objectMapper.readValue(retrievedEvent.data, clazz);
    }

}
