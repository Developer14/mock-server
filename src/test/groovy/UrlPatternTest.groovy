import org.vmdevel.mockserver.model.RequestModel
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Array
import java.util.regex.Pattern

class UrlPatternTest extends Specification {


    def "fakeTest"() {
        var pattern = Pattern.compile("(?:https?:\\/\\/)(?:www.)?([a-zA-Z0-9-]+\\.)+([a-zA-Z0-9-]{2,6})+(?:\\:\\d{4})?");
        //var pattern = Pattern.compile("[.]");
        var result;

        when:
        result = pattern.matcher("http://vmorales.cl/api/resource")
        //result.find()

        println result.find()
        println result.group()

        then:
        result != null
    }

    @Unroll
    def "newRequestModelTest_shouldSplitDomainAndPath"() {

        var requestModel

        //given: "we create a list"

        when: "we remove item with index 3"
        requestModel = new RequestModel(method, url, null)
        //list.remove(index)

        then: "url parts"
        requestModel.url.domain == result[0] && requestModel.url.path == result[1]

        where:
        method   |   url                                                        |   result
        "GET"    |   "http://dms-dsr.vmorales.cl/api/resource"                  |   ["http://dms-dsr.vmorales.cl", "/api/resource"]
        "POST"   |   "https://vmorales.cl/api/resource"                         |   ["https://vmorales.cl", "/api/resource"]
        "PUT"    |   "http://vmorales.cl:7320/api/resource"                     |   ["http://vmorales.cl:7320", "/api/resource"]
        "DELETE" |   "http://vmorales.cl/api/resource?param1=yuyo"              |   ["http://vmorales.cl", "/api/resource?param1=yuyo"]
        "PATCH"  |   "https://vmorales.cl:7832/api/resource"                    |   ["https://vmorales.cl:7832", "/api/resource"]
        "GET"    |   "https://www.vmorales.cl:7832/api/resource"                |   ["https://www.vmorales.cl:7832", "/api/resource"]
        "GET"    |   "https://www.dms.vmorales.cl:7832/api/resource"            |   ["https://www.dms.vmorales.cl:7832", "/api/resource"]
        "POST"   |   "http://www.dms.prod.v2.vmorales.cl:7832/api/resource"     |   ["http://www.dms.prod.v2.vmorales.cl:7832", "/api/resource"]
        "PUT"    |   "https://190.130.42.100:7832/api/resource"                 |   ["https://190.130.42.100:7832", "/api/resource"]
        "GET"    |   "https://190.130.42.100/api/resource"                      |   ["https://190.130.42.100", "/api/resource"]
        "DELETE" |   "http://190.130.42.100:7832/api/resource"                  |   ["http://190.130.42.100:7832", "/api/resource"]
        "GET"    |   "http://localhost:4200/sockjs-node/info?t=1761085171676"   |   ["http://localhost:4200", "/sockjs-node/info?t=1761085171676"]
    }
}
