package pt.canaverde.robotcleaner.service

import pt.canaverde.robotcleaner.domain.cleaning.CleaningInstruction
import pt.canaverde.robotcleaner.domain.space.Area
import pt.canaverde.robotcleaner.domain.space.CardinalDirection
import pt.canaverde.robotcleaner.domain.space.Coordinates
import pt.canaverde.robotcleaner.domain.space.Location
import pt.canaverde.robotcleaner.domain.space.Surface
import spock.lang.Shared
import spock.lang.Specification

class SeaCleaningServiceSpec extends Specification {
    @Shared
    CleaningService cleaningService = new SeaCleaningService()

    def "follows cleaning instruction"() {
        when:
        def area = new Area(5, 5, Surface.SEA)

        area.updateLocation(new Location(new Coordinates(0, 0), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(0, 1), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(0, 2), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(1, 2), Surface.OIL))

        def initialPosition = new Coordinates(0, 0)
        def directions = [CardinalDirection.NORTH, CardinalDirection.NORTH, CardinalDirection.EAST]

        def cleaningInstruction = new CleaningInstruction(area, initialPosition, directions)
        def result = cleaningService.clean(cleaningInstruction)

        then:
        result.finalPosition == new Coordinates(1, 2)
        result.locationsCleaned == 4
        result.outOfBoundsError.isEmpty()
    }

    def "cleaning result includes error if the cleaner goes out of bounds"() {
        when:
        def area = new Area(5, 5, Surface.SEA)

        area.updateLocation(new Location(new Coordinates(0, 0), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(0, 1), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(0, 2), Surface.OIL))
        area.updateLocation(new Location(new Coordinates(1, 2), Surface.OIL))

        def initialPosition = new Coordinates(0, 0)
        def directions = [CardinalDirection.NORTH, CardinalDirection.NORTH, CardinalDirection.WEST]

        def cleaningInstruction = new CleaningInstruction(area, initialPosition, directions)
        def result = cleaningService.clean(cleaningInstruction)

        then:
        result.finalPosition == new Coordinates(0, 2)
        result.locationsCleaned == 3
        result.outOfBoundsError.isPresent()

        result.outOfBoundsError.get().invalidPosition == new Coordinates(-1, 2)
        result.outOfBoundsError.get().indexOfInvalidCardinalDirection.get() == 2
        result.outOfBoundsError.get().lastPosition == new Coordinates(0, 2)
    }
}
