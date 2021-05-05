package modzo.compare.query.core.domain.events

class ComparisonCreated implements Event {
    static final String NAME = 'COMPARISON_CREATED'

    String uniqueId

    String firstItemUniqueId

    String secondItemUniqueId

    int sequence

    ComparisonCreated() {
        //For Json object mapper
    }

    ComparisonCreated(String uniqueId, String firstItemUniqueId, String secondItemUniqueId, int sequence) {
        this.uniqueId = uniqueId
        this.firstItemUniqueId = firstItemUniqueId
        this.secondItemUniqueId = secondItemUniqueId
        this.sequence = sequence
    }

    @Override
    String topic() {
        return NAME
    }

    @Override
    String uniqueId() {
        return uniqueId
    }
}
