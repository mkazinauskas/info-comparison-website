package modzo.compare.query.core.api.utils

import groovy.json.JsonOutput
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Component

import java.lang.reflect.ParameterizedType

@Component
class RequestTemplate {

    private TestRestTemplate restTemplate

    RequestTemplate(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    ResponseEntity<String> post(String url, Map<String, String> request) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity<String> entity = new HttpEntity<String>(JsonOutput.toJson(request), headers)

        return restTemplate.postForEntity(url, entity, String)
    }

    ResponseEntity<String> get(String url) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        return restTemplate.getForEntity(url, String)
    }

    def <T> ResponseEntity<T> getForEntity(String url, Class<T> clazz) {
        return restTemplate.getForEntity(url, clazz)
    }
}
