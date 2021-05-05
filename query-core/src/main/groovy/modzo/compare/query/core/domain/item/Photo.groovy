package modzo.compare.query.core.domain.item

import modzo.compare.query.core.domain.AbstractEntity
import org.elasticsearch.common.inject.name.Named
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'photos', type = 'photos', refreshInterval = '-1')
class Photo extends AbstractEntity {
    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'itemUniqueId')
    String itemUniqueId

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'name')
    String name

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'description')
    String description

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'url_md5')
    String urlMD5

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    @Named(value = 'sequence')
    int sequence
}
