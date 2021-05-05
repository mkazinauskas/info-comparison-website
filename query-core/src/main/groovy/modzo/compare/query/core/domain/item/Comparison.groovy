package modzo.compare.query.core.domain.item

import modzo.compare.query.core.domain.AbstractEntity
import org.elasticsearch.common.inject.name.Named
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'comparisons', type = 'comparisons', refreshInterval = '-1')
class Comparison extends AbstractEntity {
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'first_item_unique_id')
    String firstItemUniqueId

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'second_item_unique_id')
    String secondItemUniqueId

    @Field(type = FieldType.Integer, index = FieldIndex.analyzed)
    @Named(value = 'sequence')
    int sequence
}
