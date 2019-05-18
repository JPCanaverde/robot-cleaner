package pt.canaverde.robotcleaner.presentation.error

import com.fasterxml.jackson.databind.ObjectMapper
import pt.canaverde.robotcleaner.Mapper
import pt.canaverde.robotcleaner.domain.space.Coordinates
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException
import spock.lang.Specification

import static pt.canaverde.robotcleaner.Mapper.objectMapper

class CleaningResultErrorSpec extends Specification {
    def "converting from exception works"() {
        when:
        def outOfBounds = new CoordinatesOutOfBoundsException(new Coordinates(1, 2), new Coordinates(3, 4))
        def error = CleaningResultError.from(outOfBounds)

        then:
        error.indexOfInvalidCardinalDirection.isEmpty()
        error.invalidPosition[0] == 3
        error.invalidPosition[1] == 4
    }

    def "converting from exception with direction index works"() {
        when:
        def outOfBounds = new CoordinatesOutOfBoundsException(1, new Coordinates(1, 2), new Coordinates(3, 4))
        def error = CleaningResultError.from(outOfBounds)

        then:
        error.indexOfInvalidCardinalDirection.get() == 1
        error.invalidPosition[0] == 3
        error.invalidPosition[1] == 4
    }

    def "serialising and deserialising with optional index works"() {
        when:
        def outOfBounds = new CoordinatesOutOfBoundsException(1, new Coordinates(3, 4), new Coordinates(5, 6))
        def error = CleaningResultError.from(outOfBounds)

        def serialisedError = objectMapper.writeValueAsString(error)

        then:
        serialisedError.contains("1")

        when:
        def deserialisedError = objectMapper.readValue(serialisedError, CleaningResultError.class)

        then:
        deserialisedError.indexOfInvalidCardinalDirection.get() == 1
    }
}
