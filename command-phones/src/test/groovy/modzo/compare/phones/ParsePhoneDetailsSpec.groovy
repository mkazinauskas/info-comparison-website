package modzo.compare.phones

import modzo.compare.command.core.domain.item.services.SaveBundleService
import modzo.compare.phones.parser.PhoneDetailsParser
import spock.lang.Specification

class ParsePhoneDetailsSpec extends Specification {

    PhoneDetailsParser parser = new PhoneDetailsParser()

    def 'should parse phone 6 details'() {
        given:
            String detailsHtml = getClass().getResource('/samples/phone-6.html').getText()
        expect:
            !detailsHtml.isEmpty()
        when:
            SaveBundleService.Bundle saveBundleService = parser.parse(detailsHtml)
        then:
            saveBundleService.name == 'Nokia 3210'
            saveBundleService.description == ''
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'Technology' }.value == 'GSM'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == '2G bands' }.value == 'GSM 900 / 1800'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'GPRS' }.value == 'No'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'EDGE' }.value == 'No'
            saveBundleService.details.find { it.key == 'Launch' && it.subKey == 'Announced' }.value == '1999'
            saveBundleService.details.find { it.key == 'Launch' && it.subKey == 'Status' }.value == 'Discontinued'

            saveBundleService.details.find {
                it.key == 'Battery' && it.subKey == ''
            }.value == 'Removable Li-Ion battery'

            saveBundleService.details.find {
                it.key == 'Misc' && it.subKey == 'Colors'
            }.value == 'User exchangeable front and back covers'

            saveBundleService.photos.first().url == 'http://cdn2.gsmarena.com/vv/bigpic/no3210b.gif'
            saveBundleService.photos.first().name == 'Nokia 3210'
        and:
            saveBundleService.photos.size() == 1
            saveBundleService.details.size() == 36
    }

    def 'should parse phone 9 details'() {
        given:
            String detailsHtml = getClass().getResource('/samples/phone-9.html').getText()
        expect:
            !detailsHtml.isEmpty()
        when:
            SaveBundleService.Bundle saveBundleService = parser.parse(detailsHtml)
        then:
            saveBundleService.name == 'Nokia 6130'
            saveBundleService.description == ''
        and:
            saveBundleService.details.size() == 42
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'Technology' }.value == 'GSM'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == '2G bands' }.value == 'GSM 1800'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'GPRS' }.value == 'No'
            saveBundleService.details.find { it.key == 'Network' && it.subKey == 'EDGE' }.value == 'No'
            saveBundleService.details.find { it.key == 'Launch' && it.subKey == 'Announced' }.value == '1998'
            saveBundleService.details.find { it.key == 'Launch' && it.subKey == 'Status' }.value == 'Discontinued'

            saveBundleService.details.find {
                it.key == 'Battery' && it.subKey == ''
            }.value == 'Removable Li-Po 600 mAh battery (BPS-1)'

            saveBundleService.details.find {
                it.key == 'Misc' && it.subKey == 'Colors'
            }.value == '2'

            saveBundleService.photos.size() == 1
            saveBundleService.photos.first().url == 'http://cdn2.gsmarena.com/vv/bigpic/no6130b.gif'
            saveBundleService.photos.first().name == 'Nokia 6130'
    }
}
