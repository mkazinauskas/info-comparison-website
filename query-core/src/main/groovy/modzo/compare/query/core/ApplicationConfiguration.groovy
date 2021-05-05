package modzo.compare.query.core

import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.URL
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Component
@ConfigurationProperties(value = 'application')
@EnableFeignClients
@EnableAsync
class ApplicationConfiguration {
    @NotNull
    CloudinaryConfiguration cloudinaryConfiguration = new CloudinaryConfiguration()

    @Min(0L)
    long nextEventPauseTime = 10000

    static class CloudinaryConfiguration {
        @NotBlank
        @URL
        String photoUrlPrefix = 'http://res.cloudinary.com/dmfaqsydt/image/upload/'
    }
}
