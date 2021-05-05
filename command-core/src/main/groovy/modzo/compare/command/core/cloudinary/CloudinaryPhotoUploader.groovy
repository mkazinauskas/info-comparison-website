package modzo.compare.command.core.cloudinary

import com.cloudinary.Cloudinary
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import modzo.compare.command.core.domain.item.commands.create.photo.PhotoUploader
import modzo.compare.command.core.domain.validators.DomainException
import org.springframework.stereotype.Component

@Component
@CompileStatic
@PackageScope
@Slf4j
class CloudinaryPhotoUploader implements PhotoUploader {

    private final Cloudinary cloudinary

    CloudinaryPhotoUploader(CloudinaryConfiguration configuration) {
        cloudinary = new Cloudinary([
                cloud_name: configuration.cloudName,
                api_key   : configuration.apiKey,
                api_secret: configuration.apiSecret
        ]);
    }

    @Override
    void upload(PhotoUploader.Photo photo) {
        try {
            cloudinary.uploader().upload(photo.url, ['public_id': photo.name]);
        } catch (IOException exception) {
            String errorMessage = "Failed to upload image: `${photo.name}` from `${photo.url}`";
            log.error(errorMessage, exception)
            throw new DomainException("CLOUDINARY_FAILED_TO_UPLOAD", errorMessage)
        }
    }
}
