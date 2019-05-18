package pt.canaverde.robotcleaner.presentation.model.sea

import pt.canaverde.robotcleaner.domain.cleaning.CleaningResult
import pt.canaverde.robotcleaner.domain.space.Coordinates
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException
import spock.lang.Specification

class SeaCleaningResultSpec extends Specification {
    def "converting from cleaning result works"() {
        when:
        def cleaningResult = new CleaningResult(new Coordinates(3, 4), 2)
        def seaCleaningResult = SeaCleaningResult.from(cleaningResult)

        then:
        seaCleaningResult.finalPosition[0] == 3
        seaCleaningResult.finalPosition[1] == 4
        seaCleaningResult.oilPatchesCleaned == 2
    }

    def "converting from a cleaning result with an error throws an API error"() {
        when:
        def cleaningResult = new CleaningResult(new Coordinates(3, 4), 2,
                new CoordinatesOutOfBoundsException(1, new Coordinates(3, 4), new Coordinates(3, 5)))
        SeaCleaningResult.from(cleaningResult)

        then:
        def error = thrown(SeaApiError)

        def seaCleaningResult = error.getPartialResult().get()

        seaCleaningResult.finalPosition[0] == 3
        seaCleaningResult.finalPosition[1] == 4
        seaCleaningResult.oilPatchesCleaned == 2

        def details = error.getDetails().get()

        details.indexOfInvalidCardinalDirection.get() == 1
        details.invalidPosition[0] == 3
        details.invalidPosition[1] == 5
    }
}
