package modzo.compare.command.core.tools

import spock.lang.Specification
import spock.lang.Unroll

import static modzo.compare.command.core.tools.Text.clean

class TextSpec extends Specification {

    @Unroll
    def 'text `#text` should be `#result`'() {
        expect:
            clean(text) == result
        where:
            text              || result
            'Text 123'        || 'Text 123'
            'Text !@#45678'   || 'Text 45678'
            ' Text  '         || 'Text'
            "test @ name !\'" || "test name '"
    }
}
