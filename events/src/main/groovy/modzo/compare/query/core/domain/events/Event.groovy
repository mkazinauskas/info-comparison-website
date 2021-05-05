package modzo.compare.query.core.domain.events

interface Event {
    String topic()

    String uniqueId()
}