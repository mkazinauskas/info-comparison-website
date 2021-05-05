package modzo.compare.query.core.api.photos

import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import static org.springframework.web.bind.annotation.RequestMethod.GET

interface PhotosResource {
    @RequestMapping(value = '/items/{uniqueId}/photos', method = GET)
    ResponseEntity<Resources<ItemPhotoBean>> getItemPhotos(@PathVariable("uniqueId") String uniqueId)
}