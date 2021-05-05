package modzo.compare.command.core.cloudinary

import org.hibernate.validator.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(value = 'application.cloudinary')
class CloudinaryConfiguration {
    @NotBlank
    String cloudName

    @NotBlank
    String apiKey

    @NotBlank
    String apiSecret
}
