package pt.canaverde.robotcleaner.domain.space

import spock.lang.Specification

class CoordinatesSpec extends Specification {
    def "getting adjacent position works"() {
        when:
        def adjacentPosition = position.getAdjacentPosition(direction)

        then:
        adjacentPosition == expectedAdjacentPosition

        where:
        position                 | direction               | expectedAdjacentPosition
        new Coordinates(5, 5) | CardinalDirection.NORTH | new Coordinates(5, 6)
        new Coordinates(5, 5) | CardinalDirection.EAST  | new Coordinates(6, 5)
        new Coordinates(5, 5) | CardinalDirection.SOUTH | new Coordinates(5, 4)
        new Coordinates(5, 5) | CardinalDirection.WEST  | new Coordinates(4, 5)
    }

    def "converting to array works"() {
        when:
        def coordinates = new Coordinates(5, 6)
        def array = coordinates.toArray()

        then:
        coordinates.x == array[0]
        coordinates.y == array[1]
    }

    def "converting from array works"() {
        when:
        int[] array = [5, 6]
        def coordinates = Coordinates.fromArray(array)

        then:
        array[0] == coordinates.x
        array[1] == coordinates.y
    }
}
