package modzo.compare.query.core.api.item_properties

import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import static org.springframework.web.bind.annotation.RequestMethod.GET

interface ItemPropertiesResource {
    @RequestMapping(value = '/items/{uniqueId}/properties', method = GET)
    ResponseEntity<Resources<ItemPropertyDescriptorBean>> getItemProperties(@PathVariable("uniqueId") String uniqueId)
}