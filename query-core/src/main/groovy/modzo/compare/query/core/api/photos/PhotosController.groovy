package modzo.compare.query.core.api.photos

import groovy.transform.CompileStatic
import modzo.compare.query.core.ApplicationConfiguration
import modzo.compare.query.core.domain.item.Photos
import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class PhotosController implements PhotosResource {
    private final Photos photos
    private final ApplicationConfiguration configuration

    PhotosController(Photos photos, ApplicationConfiguration configuration) {
        this.photos = photos
        this.configuration = configuration
    }

    @Override
    ResponseEntity<Resources<ItemPhotoBean>> getItemPhotos(@PathVariable("uniqueId") String uniqueId) {
        return ResponseEntity.ok(new Resources(photos.findByItemUniqueId(uniqueId).collect {
            new ItemPhotoBean(
                    uniqueId: it.uniqueId,
                    sequence: it.sequence,
                    name: it.name,
                    description: it.description,
                    url: configuration.cloudinaryConfiguration.photoUrlPrefix + it.urlMD5
            )
        }))
    }
}
