package modzo.compare.query.core.events.parsers

import modzo.compare.query.core.domain.events.services.EventService

interface EventParser {
    String type()

    void parse(EventService.RetrievedEvent event)
}