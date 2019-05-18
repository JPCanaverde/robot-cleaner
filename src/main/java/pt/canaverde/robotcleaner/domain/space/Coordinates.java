package pt.canaverde.robotcleaner.domain.space;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pt.canaverde.robotcleaner.exceptions.InvalidCardinalDirectionException;

/**
 * The [X, Y] coordinates that determine positions in an area.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Coordinates {
    private final int x;
    private final int y;

    /**
     * The position adjacent to these coordinates in the direction specified.
     *
     * @param direction to move in.
     * @return the coordinates if moving in that direction.
     */
    public Coordinates getAdjacentPosition(CardinalDirection direction) {
        switch (direction) {
            case NORTH:
                return new Coordinates(this.x, this.y + 1);
            case EAST:
                return new Coordinates(this.x + 1, this.y);
            case SOUTH:
                return new Coordinates(this.x, this.y - 1);
            case WEST:
                return new Coordinates(this.x - 1, this.y);
            default:
                throw new RuntimeException(new InvalidCardinalDirectionException());
        }
    }

    /**
     * Converts an array with 2 elements to [X, Y] coordinates.
     *
     * @param coordinatesArray to convert.
     * @return the coordinates.
     */
    public static Coordinates fromArray(int[] coordinatesArray) {
        return new Coordinates(coordinatesArray[0], coordinatesArray[1]);
    }

    /**
     * Converts these coordinates to an array with two elements representing X and Y.
     *
     * @return the array.
     */
    public int[] toArray() {
        return new int[] {this.getX(), this.getY()};
    }
}
