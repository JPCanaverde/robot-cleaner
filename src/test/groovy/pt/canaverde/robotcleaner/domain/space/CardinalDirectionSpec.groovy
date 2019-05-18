package pt.canaverde.robotcleaner.domain.space

import spock.lang.Specification

class CardinalDirectionSpec extends Specification {
    def "cardinal character mapping works"() {
        when:
        def direction = CardinalDirection.fromCardinalCharacter(character.charAt(0))

        then:
        (expectedDirection && direction.get() == expectedDirection) || (!expectedDirection && direction.isEmpty())

        where:
        character | expectedDirection
        'N' | CardinalDirection.NORTH
        'E' | CardinalDirection.EAST
        'S' | CardinalDirection.SOUTH
        'W' | CardinalDirection.WEST
        'A' | null
    }
}
