package modzo.compare.phones.parser

import groovy.util.logging.Slf4j
import modzo.compare.phones.PhonesParserConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Conditional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@Slf4j
@ConditionalOnProperty(name = "application.phones.parser.job.enabled", havingValue = "true")
class PhonesParserJob {

    private final PhonesParserConfiguration phonesParserConfiguration

    private final ParserExecutor parserExecutor

    PhonesParserJob(PhonesParserConfiguration phonesParserConfiguration, ParserExecutor parserExecutor) {
        this.phonesParserConfiguration = phonesParserConfiguration
        this.parserExecutor = parserExecutor
    }

    @Scheduled(fixedDelay = 1L)
    void parse() {
        log.info('Starting execute phones parser job')
        (phonesParserConfiguration.gsmArenaFetcher.from..phonesParserConfiguration.gsmArenaFetcher.to).each { page ->
            log.info("Searching page ${page}")
            parserExecutor.parsePage(page)
        }
        log.info('Finished executing phones parser job')
        Thread.sleep(phonesParserConfiguration.gsmArenaFetcher.sleepMillis)
    }
}
