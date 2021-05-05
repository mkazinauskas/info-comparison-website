package modzo.compare.phones

import liquibase.util.Validate
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.NotNull

@Component
@Validated
@ConfigurationProperties(value = 'application.phones')
class PhonesParserConfiguration {
    @NotNull
    GsmArenaFetcherConfiguration gsmArenaFetcher = new GsmArenaFetcherConfiguration()

    static class GsmArenaFetcherConfiguration {
        @NotNull
        String url = 'http://gsmarena.com'

        int from = 1
        int to = 100
        boolean retryOfFailed = true
        long sleepMillis = 100000
    }
}
