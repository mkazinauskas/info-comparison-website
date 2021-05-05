package modzo.compare.command.core

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(value = 'application')
class ApplicationConfiguration {
}
