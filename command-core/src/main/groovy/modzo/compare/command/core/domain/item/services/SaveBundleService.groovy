package modzo.compare.command.core.domain.item.services

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import modzo.compare.command.core.domain.item.Item
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.item.PropertyDescriptor
import modzo.compare.command.core.domain.item.PropertyDescriptors
import modzo.compare.command.core.domain.item.commands.create.item.CreateItem
import modzo.compare.command.core.domain.item.commands.create.item.CreateItemHandler
import modzo.compare.command.core.domain.item.commands.create.photo.CreatePhoto
import modzo.compare.command.core.domain.item.commands.create.photo.CreatePhotoHandler
import modzo.compare.command.core.domain.item.commands.create.property.AddItemProperty
import modzo.compare.command.core.domain.item.commands.create.property.AddItemPropertyHandler
import modzo.compare.command.core.domain.item.commands.create.property_descriptor.CreatePropertyDescriptor
import modzo.compare.command.core.domain.item.commands.create.property_descriptor.CreatePropertyDescriptorHandler
import modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor.CreateSubPropertyDescriptor
import modzo.compare.command.core.domain.item.commands.create.sub_property_descriptor.CreateSubPropertyDescriptorHandler
import modzo.compare.command.core.tools.Text
import org.springframework.stereotype.Component

@Component
@CompileStatic
@Slf4j
class SaveBundleService {

    private final CreatePhotoHandler createPhotoHandler

    private final CreatePropertyDescriptorHandler createPropertyDescriptorHandler

    private final CreateSubPropertyDescriptorHandler createSubPropertyDescriptorHandler

    private final PropertyDescriptors propertyDescriptors

    private final CreateItemHandler createItemHandler

    private final AddItemPropertyHandler addItemPropertyHandler

    private final Items items

    SaveBundleService(CreatePhotoHandler createPhotoHandler, CreatePropertyDescriptorHandler createPropertyDescriptorHandler, CreateSubPropertyDescriptorHandler createSubPropertyDescriptorHandler, PropertyDescriptors propertyDescriptors,
                      CreateItemHandler createItemHandler, AddItemPropertyHandler addItemPropertyHandler, Items items) {
        this.createPhotoHandler = createPhotoHandler
        this.createPropertyDescriptorHandler = createPropertyDescriptorHandler
        this.createSubPropertyDescriptorHandler = createSubPropertyDescriptorHandler
        this.propertyDescriptors = propertyDescriptors
        this.createItemHandler = createItemHandler
        this.addItemPropertyHandler = addItemPropertyHandler
        this.items = items
    }

    String save(Bundle bundle) {
        Optional<Item> foundItem = items.findByName(Text.clean(bundle.name))
        String itemUniqueId = ''
        if (foundItem.isPresent()) {
            itemUniqueId = foundItem.get().uniqueId
        } else {
            CreateItem item = new CreateItem(
                    name: bundle.name,
                    description: bundle.description,
                    identifier: bundle.identifier
            )
            createItemHandler.handle(item)
            itemUniqueId = item.uniqueId
        }

        bundle.details.each { detail ->
            try {
                String propertyDescriptorUniqueId
                Optional<PropertyDescriptor> propertyDescriptor = propertyDescriptors.findByName(detail.key)
                if (propertyDescriptors.findByName(detail.key).isPresent()) {
                    log.debug("Property descriptor `${detail.key}` already exists")
                    propertyDescriptorUniqueId = propertyDescriptor.get().uniqueId
                } else {
                    CreatePropertyDescriptor createPropertyDescriptor = new CreatePropertyDescriptor(
                            name: detail.key
                    )
                    propertyDescriptorUniqueId = createPropertyDescriptor.uniqueId
                    createPropertyDescriptorHandler.handle(createPropertyDescriptor)
                }

                if (detail.subKey) {
                    Optional<PropertyDescriptor> existingSubProperty =
                            propertyDescriptors.findByParentPropertyDescriptorUniqueIdAndName(
                                    propertyDescriptorUniqueId,
                                    detail.subKey)
                    if (existingSubProperty.isPresent()) {
                        log.debug("Property descriptor sub key `${detail.subKey}` already exists")
                    } else {
                        CreateSubPropertyDescriptor createSubPropertyDescriptor = new CreateSubPropertyDescriptor(
                                name: detail.subKey,
                                parentPropertyDescriptorUniqueId: propertyDescriptorUniqueId
                        )
                        createSubPropertyDescriptorHandler.handle(createSubPropertyDescriptor)
                    }
                }
            } catch (Exception exception) {
                log.error("Failed to save details `${detail.key} and `$detail.subKey`", exception)
            }
        }

        bundle.details.each { detail ->
            try {
                String descriptorUniqueId = propertyDescriptors.findByName(detail.key).get().uniqueId
                if (detail.subKey) {
                    descriptorUniqueId = propertyDescriptors.findByParentPropertyDescriptorUniqueIdAndName(descriptorUniqueId, detail.subKey).get().uniqueId
                }

                AddItemProperty addItemProperty = new AddItemProperty(
                        itemUniqueId: itemUniqueId,
                        propertyDescriptorUniqueId: descriptorUniqueId,
                        value: detail.value
                )
                addItemPropertyHandler.handle(addItemProperty)
            } catch (Exception exception) {
                log.error("Failed to save details `${detail.key}` and `${detail.subKey}`", exception)
            }
        }


        bundle.photos.each { photo ->
            try {
                CreatePhoto createPhoto = new CreatePhoto(
                        itemUniqueId: itemUniqueId,
                        name: photo.name,
                        url: photo.url,
                        description: photo.description ?: photo.name
                )

                createPhotoHandler.handle(createPhoto)
            } catch (Exception exception) {
                log.error('Failed to save photo', exception)
            }
        }

        return itemUniqueId
    }

    static class Bundle {
        String identifier
        String name
        String description

        final List<Photo> photos = []

        final List<Property> details = []

        @EqualsAndHashCode(includeFields = true)
        static class Property {
            String key, subKey, value
        }

        static class Photo {
            String name, description, url

            Photo(String name, String description, String url) {
                this.name = name
                this.description = description
                this.url = url
            }
        }
    }
}
