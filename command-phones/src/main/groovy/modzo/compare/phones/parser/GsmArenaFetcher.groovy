package modzo.compare.phones.parser

import groovy.util.logging.Slf4j
import modzo.compare.phones.PhonesParserConfiguration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import static java.util.Optional.empty
import static java.util.Optional.ofNullable
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@Slf4j
@Component
class GsmArenaFetcher {

    private final PhonesParserConfiguration phonesParserConfiguration
    private final RestTemplate restTemplate

    GsmArenaFetcher(
            PhonesParserConfiguration phonesParserConfiguration,
            @Qualifier('plainRestTemplate') RestTemplate restTemplate) {
        this.phonesParserConfiguration = phonesParserConfiguration
        this.restTemplate = restTemplate
    }

    Optional<String> fetch(int id) {
        try {
            HttpHeaders headers = new HttpHeaders()
            headers.add('Accept', 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8')
            headers.add('Accept-Language', 'en-US,en;q=0.8,lt;q=0.6,fi;q=0.4')
            headers.add('Host', 'www.gsmarena.com')
            headers.add('User-Agent', 'Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36')

            HttpEntity<String> entity = new HttpEntity<String>(headers)

            ResponseEntity<String> response = restTemplate.exchange("${phonesParserConfiguration.gsmArenaFetcher.url}/${randomAlphanumeric(10)}-${id}.php", HttpMethod.GET, entity, String)
            if (response.getStatusCode() != HttpStatus.OK) {
                log.warn('Response contains status code {} which is not OK', response.getStatusCode())
                return empty();
            }
            String body = response.getBody()
            return ofNullable(body)
        } catch (Exception ex) {
            log.error('Fetch phone from gsm arena failed', ex)
            return empty()
        }
    }
}
