package pt.canaverde.robotcleaner.presentation.error;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException;

import java.util.Optional;

/**
 * Describes an illegal movement that occurred while cleaning.
 */
@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = CleaningResultError.CleaningResultErrorBuilder.class)
public class CleaningResultError {
    /**
     * The index in the list the cardinal directions that caused this exception to be thrown.
     * May be null.
     */
    private final Integer indexOfInvalidCardinalDirection;

    /**
     * The position that would be out of bounds.
     */
    private final int[] invalidPosition;

    public Optional<Integer> getIndexOfInvalidCardinalDirection() {
        return Optional.ofNullable(this.indexOfInvalidCardinalDirection);
    }

    /**
     * Transforms an out of bounds exception to an API CleaningResultError.
     *
     * @param outOfBoundsError the exception thrown while cleaning.
     * @return the API CleaningResultError.
     */
    public static CleaningResultError from(CoordinatesOutOfBoundsException outOfBoundsError) {
        return outOfBoundsError.getIndexOfInvalidCardinalDirection()
                .map(index -> new CleaningResultError(index, outOfBoundsError.getInvalidPosition().toArray()))
                .orElse(new CleaningResultError(outOfBoundsError.getInvalidPosition().toArray()));
    }

    private CleaningResultError(int[] invalidPosition) {
        this(null, invalidPosition);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CleaningResultErrorBuilder {

    }
}
