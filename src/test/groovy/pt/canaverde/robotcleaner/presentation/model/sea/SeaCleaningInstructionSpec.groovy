package pt.canaverde.robotcleaner.presentation.model.sea

import pt.canaverde.robotcleaner.domain.space.CardinalDirection
import pt.canaverde.robotcleaner.domain.space.Coordinates
import pt.canaverde.robotcleaner.domain.space.Surface
import pt.canaverde.robotcleaner.exceptions.InvalidCardinalDirectionException
import pt.canaverde.robotcleaner.presentation.error.ApiError
import spock.lang.Specification

class SeaCleaningInstructionSpec extends Specification {
    def "converting to cleaning instruction works"() {
        when:
        def seaCleaningInstruction = SeaCleaningInstruction.builder()
                .areaSize([5, 5] as int[])
                .startingPosition([0, 0] as int[])
                .oilPatches([[1, 1] as int[], [2, 2] as int[]])
                .navigationInstructions("N    E s  w")
                .build()

        def cleaningInstruction = seaCleaningInstruction.toCleaningInstruction()

        then:
        cleaningInstruction.area.locationExists(new Coordinates(0, 0))
        cleaningInstruction.area.locationExists(new Coordinates(4, 4))

        !cleaningInstruction.area.locationExists(new Coordinates(5, 4))
        !cleaningInstruction.area.locationExists(new Coordinates(4, 5))

        cleaningInstruction.initialPosition == new Coordinates(0, 0)

        cleaningInstruction.area.getLocation(new Coordinates(1, 1)).get().getSurface() == Surface.OIL
        cleaningInstruction.area.getLocation(new Coordinates(2, 2)).get().getSurface() == Surface.OIL

        cleaningInstruction.area.getLocation(new Coordinates(3, 3)).get().getSurface() == Surface.SEA

        cleaningInstruction.directions[0] == CardinalDirection.NORTH
        cleaningInstruction.directions[1] == CardinalDirection.EAST
        cleaningInstruction.directions[2] == CardinalDirection.SOUTH
        cleaningInstruction.directions[3] == CardinalDirection.WEST
    }

    def "oil patches must have 2 dimensions"() {
        when:
        def seaCleaningInstruction = SeaCleaningInstruction.builder()
                .areaSize([5, 5] as int[])
                .startingPosition([0, 0] as int[])
                .oilPatches([[1, 1, 1] as int[]])
                .navigationInstructions("N    E s  w")
                .build()

        seaCleaningInstruction.toCleaningInstruction()

        then:
        thrown(ApiError)

        when:
        seaCleaningInstruction = SeaCleaningInstruction.builder()
                .areaSize([5, 5] as int[])
                .startingPosition([0, 0] as int[])
                .oilPatches([[1] as int[]])
                .navigationInstructions("N    E s  w")
                .build()

        seaCleaningInstruction.toCleaningInstruction()

        then:
        thrown(ApiError)
    }

    def "invalid oil patches throw an API error"() {
        when:
        def seaCleaningInstruction = SeaCleaningInstruction.builder()
                .areaSize([5, 5] as int[])
                .startingPosition([0, 0] as int[])
                .oilPatches([[5, 5] as int[], [2, 2] as int[]])
                .navigationInstructions("N    E s  w")
                .build()

        seaCleaningInstruction.toCleaningInstruction()

        then:
        thrown(ApiError)
    }

    def "invalid cardinal directions throw an exception"() {
        when:
        def seaCleaningInstruction = SeaCleaningInstruction.builder()
                .areaSize([5, 5] as int[])
                .startingPosition([0, 0] as int[])
                .oilPatches([[1, 1] as int[], [2, 2] as int[]])
                .navigationInstructions("N    A s  w")
                .build()

        seaCleaningInstruction.toCleaningInstruction()

        then:
        thrown(InvalidCardinalDirectionException)
    }
}