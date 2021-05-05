package modzo.compare.ui

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Render404page extends Specification {

    @Autowired
    TestRestTemplate testRestTemplate

    def 'should load 404 page'() {
        given:
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.TEXT_HTML)
            headers.setAccept([MediaType.TEXT_HTML])
            HttpEntity<String> entity = new HttpEntity<String>(headers)
        when:
            def result = testRestTemplate.exchange('/404.html', HttpMethod.GET, entity, String)
        then:
            result.statusCode == HttpStatus.NOT_FOUND
        and:
            result.body.contains('error blet')
    }
}
