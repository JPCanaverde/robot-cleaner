package pt.canaverde.robotcleaner.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;
import pt.canaverde.robotcleaner.domain.space.Coordinates;

import java.util.Optional;

/**
 * To be thrown when a set of coordinates are out of the boundaries of an area.
 */
@Getter
@Wither
@AllArgsConstructor
public class CoordinatesOutOfBoundsException extends Exception {
    /**
     * The index in the list the cardinal directions that caused this exception to be thrown.
     * May be null.
     */
    private final Integer indexOfInvalidCardinalDirection;

    /**
     * The last valid position before this exception being thrown.
     */
    private final Coordinates lastPosition;

    /**
     * The position that would be out of bounds.
     */
    private final Coordinates invalidPosition;

    public CoordinatesOutOfBoundsException(Coordinates lastPosition, Coordinates invalidPosition) {
        this.indexOfInvalidCardinalDirection = null;
        this.lastPosition = lastPosition;
        this.invalidPosition = invalidPosition;
    }

    public Optional<Integer> getIndexOfInvalidCardinalDirection() {
        return Optional.ofNullable(indexOfInvalidCardinalDirection);
    }
}
