package modzo.compare.command.core.domain.item

import modzo.compare.command.core.domain.AbstractEntity
import org.apache.commons.codec.digest.DigestUtils

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'photos')
class Photo extends AbstractEntity {
    @Column(name = 'itemUniqueId', nullable = false)
    String itemUniqueId

    @Column(name = 'name', nullable = false)
    String name

    @Column(name = 'description', nullable = false)
    String description

    @Column(name = 'url_md5', nullable = false)
    String urlMD5

    @Column(name = 'sequence')
    int sequence

    void assignUrlMd5(String url) {
        urlMD5 = DigestUtils.md5Hex(url)
    }
}
