package pt.canaverde.robotcleaner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RobotCleanerApplicationSpec extends Specification {
    @Autowired
    RobotCleanerApplication app

    def "application starts"() {
        when:
        def clazz = app.getClass()

        then:
        clazz != null
    }
}