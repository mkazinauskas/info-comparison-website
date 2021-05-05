package modzo.compare.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConfigApplicationTest extends Specification {
    @Autowired
    private ApplicationContext applicationContext

    def 'should load application context'() {
        expect:
            applicationContext != null
    }
}
