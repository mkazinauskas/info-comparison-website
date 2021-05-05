package modzo.compare.ui

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.Min

@Component
@Validated
@ConfigurationProperties(value = 'application')
class UiConfigurationProperties {
}
