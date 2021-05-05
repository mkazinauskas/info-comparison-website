package modzo.compare.phones.parser

import groovy.util.logging.Slf4j
import modzo.compare.command.core.domain.item.services.SaveBundleService
import org.ccil.cowan.tagsoup.Parser

@Slf4j
class PhoneDetailsParser {

    static SaveBundleService.Bundle parse(String pageContent) {
        def content = new XmlSlurper(new Parser()).parseText(pageContent)
        return new SaveBundleService.Bundle(
                name: content.'**'.find { it['@class'] == 'specs-phone-name-title' }.toString(),
                description: ''
        ).with { bundle ->

            try {
                parseDetails(bundle, content)
            } catch (ignored) {
                log.error("PhoneDetailsParser failed to parse details with", ignored)
            }

            try {
                parsePhotos(bundle, content)
            } catch (ignored) {
                log.error("PhoneDetailsParser failed to parse photos with", ignored)
            }

            return bundle
        }
    }

    private static void parseDetails(SaveBundleService.Bundle bundle, def content) {
        content.'**'.findAll { it.name() == 'table' && it['@cellspacing'] == '0' }.each {
            String category = it.tr.th.toString()
            it.tr.each { tr ->
                String key = tr.td[0].toString().trim()
                bundle.details.add(new SaveBundleService.Bundle.Property(
                        key: category,
                        subKey: isEmpty(key) ? '' : key,
                        value: tr.td[1].toString().trim()
                ))
            }
        }
    }

    private static void parsePhotos(SaveBundleService.Bundle bundle, def content) {
        content.'**'.findAll { it.name() == 'div' && it['@class'] == 'specs-photo-main' }.each {
            it.'**'.findAll { it.name() == 'img' }.each { img ->
                String name = img['@alt'].toString().trim().replace(' MORE PICTURES', '')

                String url = img['@src'].toString().trim()
                if (name && url) {
                    bundle.photos.add(new SaveBundleService.Bundle.Photo(
                            name, '', url
                    ))
                }
            }
        }
    }

    private static boolean isEmpty(String string) {
        return string.isEmpty() || string == (char) 160
    }
}
