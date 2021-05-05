package modzo.compare.query.core.events

import groovy.util.logging.Slf4j
import modzo.compare.query.core.ApplicationConfiguration
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.query.core.domain.parser.ParsedEvents
import modzo.compare.query.core.domain.parser.command.CreateParsedEvent
import modzo.compare.query.core.domain.parser.command.CreateParsedEventHandler
import modzo.compare.query.core.domain.parser.services.LatestParsedEventService
import modzo.compare.query.core.events.parsers.EventParser
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Slf4j
@Component
@ConditionalOnProperty(name = "application.events.parser.job.enabled", havingValue = "true")
class EventParserJob {

    private final EventService eventService

    private final ParsedEvents parsedEvents

    private final Map<String, EventParser> eventParsers

    private final CreateParsedEventHandler createParsedEventHandler

    private final LatestParsedEventService latestParsedEventService

    private final ApplicationConfiguration applicationConfiguration

    EventParserJob(EventService eventService, ParsedEvents parsedEvents,
                   Collection<EventParser> eventParsers,
                   CreateParsedEventHandler createParsedEventHandler,
                   LatestParsedEventService latestParsedEventService,
                   ApplicationConfiguration applicationConfiguration
    ) {
        this.eventService = eventService
        this.parsedEvents = parsedEvents
        this.eventParsers = eventParsers.collectEntries { [(it.type()): it] }
        this.createParsedEventHandler = createParsedEventHandler
        this.latestParsedEventService = latestParsedEventService
        this.applicationConfiguration = applicationConfiguration
    }

    @Scheduled(fixedDelay = 1L)
    void parse() {
        long lastEventId = 0
        latestParsedEventService.latestParsedEvent().ifPresent {
            lastEventId = it.id
        }
        Optional<EventService.RetrievedEvent> nextEvent = eventService.retrieveNext(lastEventId)
        if (!nextEvent.isPresent()) {
            log.debug("No event found. Pausing retrieval for ${applicationConfiguration.nextEventPauseTime}")
            sleep(applicationConfiguration.nextEventPauseTime)
            return
        }
        EventService.RetrievedEvent event = nextEvent.get()
        log.debug("Event found. Processing: ${event.id}")

        EventParser eventParser = eventParsers.get(event.type)
        if (eventParser) {
            eventParser.parse(event)
            createParsedEventHandler.handle(new CreateParsedEvent(event.id))
        } else {
            throw new EventCouldNotBeParsedException(event.type)
        }
    }

}
