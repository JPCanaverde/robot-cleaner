package pt.canaverde.robotcleaner.domain.cleaning

import pt.canaverde.robotcleaner.domain.space.Area
import pt.canaverde.robotcleaner.domain.space.CardinalDirection
import pt.canaverde.robotcleaner.domain.space.Coordinates
import pt.canaverde.robotcleaner.domain.space.Location
import pt.canaverde.robotcleaner.domain.space.Surface
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException
import spock.lang.Specification
import spock.lang.Unroll

class SimpleCleanerSpec extends Specification {
    @Unroll
    def "cleaning an area works"() {
        when:
        Cleaner cleaner = new SeaCleaner(initialPosition)

        def area = new Area(areaSize, areaSize, Surface.SEA)

        oilPatches.forEach({ patch -> area.updateLocation(new Location(patch, Surface.OIL)) })

        cleaner.clean(area, directions)

        then:
        cleaner.locationsCleaned == oilPatches.size()

        where:
        areaSize | oilPatches                                                                                   | initialPosition       | directions
        5        | [new Coordinates(0, 0)]                                                                      | new Coordinates(0, 0) | []
        5        | [new Coordinates(0, 0), new Coordinates(0, 1)]                                               | new Coordinates(0, 0) | [CardinalDirection.NORTH]
        5        | [new Coordinates(0, 0), new Coordinates(0, 1), new Coordinates(1, 1)]                        | new Coordinates(0, 0) | [CardinalDirection.NORTH, CardinalDirection.EAST]
        5        | [new Coordinates(0, 0), new Coordinates(0, 1), new Coordinates(1, 1), new Coordinates(1, 0)] | new Coordinates(0, 0) | [CardinalDirection.NORTH, CardinalDirection.EAST, CardinalDirection.SOUTH]
        5        | [new Coordinates(0, 0), new Coordinates(0, 1), new Coordinates(1, 1), new Coordinates(1, 0)] | new Coordinates(0, 0) | [CardinalDirection.NORTH, CardinalDirection.EAST, CardinalDirection.SOUTH, CardinalDirection.WEST]

    }

    @Unroll
    def "cleaner cannot move out of the bounds of an area"() {
        when:
        Cleaner cleaner = new SeaCleaner(initialPosition)

        def area = new Area(areaSize, areaSize, Surface.SEA)

        cleaner.clean(area, directions)

        then:
        thrown(CoordinatesOutOfBoundsException)

        where:
        areaSize | initialPosition       | directions
        5        | new Coordinates(0, 0) | [CardinalDirection.SOUTH]
        5        | new Coordinates(0, 0) | [CardinalDirection.WEST]
        5        | new Coordinates(4, 4) | [CardinalDirection.NORTH]
        5        | new Coordinates(4, 4) | [CardinalDirection.EAST]
        5        | new Coordinates(5, 5) | []
    }
}
