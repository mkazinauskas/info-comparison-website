package modzo.compare.command.api.compare

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

interface ComparisonResource {
    @RequestMapping(value = '/comparison', method = POST)
    ResponseEntity createComparison(@RequestBody ComparisonRequest request)

    @RequestMapping(value = '/comparison/{comparisonUniqueId}', method = GET)
    ResponseEntity<ComparisonBean> retrieveComparison(@PathVariable('comparisonUniqueId') String comparisonUniqueId)
}