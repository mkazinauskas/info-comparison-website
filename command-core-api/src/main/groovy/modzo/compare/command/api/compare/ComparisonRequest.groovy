package modzo.compare.command.api.compare

import org.hibernate.validator.constraints.NotBlank

class ComparisonRequest {
    @NotBlank
    String firstItemUniqueId

    @NotBlank
    String secondItemUniqueId

    ComparisonRequest() {
        //for deserializer
    }

    ComparisonRequest(String firstItemUniqueId, String secondItemUniqueId) {
        this.firstItemUniqueId = firstItemUniqueId
        this.secondItemUniqueId = secondItemUniqueId
    }
}
