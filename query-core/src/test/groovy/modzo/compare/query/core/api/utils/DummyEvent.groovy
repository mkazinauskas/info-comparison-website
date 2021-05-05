package modzo.compare.query.core.api.utils

import org.springframework.stereotype.Component

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang.RandomStringUtils.randomAscii

@Component
class DummyEvent {

    private final TestContext testContext

    DummyEvent(TestContext testContext) {
        this.testContext = testContext
    }

    static Map<String, String> sampleRequest() {
        [
                uniqueId: randomAlphanumeric(100),
                topic   : randomAlphanumeric(100),
                value   : randomAscii(10000)
        ]
    }

    void create(Map<String, String> request = sampleRequest()) {
        testContext.createEvent(request)
    }
}
