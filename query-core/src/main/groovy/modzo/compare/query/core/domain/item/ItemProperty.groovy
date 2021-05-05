package modzo.compare.query.core.domain.item

import modzo.compare.query.core.domain.AbstractEntity
import org.elasticsearch.common.inject.name.Named
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'item_properties', type = 'item_properties', refreshInterval = '-1')
class ItemProperty extends AbstractEntity {
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'uniqueId')
    String itemUniqueId

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'propertyDescriptorUniqueId')
    String propertyDescriptorUniqueId

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'value')
    String value
}
