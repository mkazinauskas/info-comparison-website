package modzo.compare.phones.parser

import modzo.compare.command.core.domain.item.Item
import modzo.compare.command.core.domain.item.Items
import modzo.compare.command.core.domain.item.commands.create.photo.PhotoUploader
import modzo.compare.query.core.domain.events.services.EventService
import modzo.compare.phones.PhonesParserConfiguration
import org.hamcrest.core.StringContains
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ParserExecutorSpec extends Specification {
    @Autowired
    ParserExecutor parserExecutor

    @Autowired
    PhonesParserConfiguration phonesParserConfiguration

    @Autowired
    @Qualifier('plainRestTemplate')
    RestTemplate restTemplate

    @MockBean
    EventService eventService

    @MockBean
    PhotoUploader photoUploader

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    Items items

    def 'should parse page'() {
        given:
            String detailsHtml = getClass().getResource('/samples/phone-6.html').getText()
        and:
            serverReturnDetails(detailsHtml)
        when:
            parserExecutor.parsePage(1)
        then:
            Item savedItem = items.findByIdentifier(1 as String).get()
            savedItem.name == 'Nokia 3210'
            savedItem.identifier == '1'
            savedItem.description == ''
            savedItem.uniqueId.length() == 40
    }

    private void serverReturnDetails(String details) {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(ExpectedCount.once(),
                requestTo(StringContains.containsString("${phonesParserConfiguration.gsmArenaFetcher.url}/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(details, MediaType.APPLICATION_JSON)
        )
    }
}
