package modzo.compare.query.core.events

class EventCouldNotBeParsedException extends RuntimeException {
    EventCouldNotBeParsedException(String eventName) {
        super("Event `${eventName}` could not be parsed")
    }
}
