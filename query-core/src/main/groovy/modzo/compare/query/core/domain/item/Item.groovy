package modzo.compare.query.core.domain.item

import modzo.compare.query.core.domain.AbstractEntity
import org.elasticsearch.common.inject.name.Named
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'items', type = 'items', refreshInterval = '-1')
class Item extends AbstractEntity {
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'name')
    String name

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'description')
    String description

    @Field(type = FieldType.Long, index = FieldIndex.analyzed)
    @Named(value = 'order')
    long order

    @Field(type = FieldType.Date, index = FieldIndex.analyzed)
    @Named(value = 'created')
    Date created = new Date()
}
