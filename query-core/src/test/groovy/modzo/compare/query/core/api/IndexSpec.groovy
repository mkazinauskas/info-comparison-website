package modzo.compare.query.core.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class IndexSpec extends Specification {

    @Autowired
    private TestRestTemplate testRestTemplate

    def 'should load index endpoint'() {
        when:
            ResponseEntity<String> response = testRestTemplate.getForEntity('/', String)
        then:
            response.statusCode == HttpStatus.OK
    }
}
