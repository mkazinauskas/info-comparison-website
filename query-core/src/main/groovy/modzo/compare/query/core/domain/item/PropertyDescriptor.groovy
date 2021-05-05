package modzo.compare.query.core.domain.item

import modzo.compare.query.core.domain.AbstractEntity
import org.elasticsearch.common.inject.name.Named
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'property_descriptors', type = 'property_descriptors', refreshInterval = '-1')
class PropertyDescriptor extends AbstractEntity {
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'parent_property_descriptor_unique_id')
    String parentPropertyDescriptorUniqueId

    @Field(type = FieldType.Long, index = FieldIndex.analyzed)
    @Named(value = 'sequence')
    long sequence

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'name')
    String name

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'details')
    String details
}
