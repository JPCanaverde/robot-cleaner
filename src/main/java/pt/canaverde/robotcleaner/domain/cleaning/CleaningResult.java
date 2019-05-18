package pt.canaverde.robotcleaner.domain.cleaning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.canaverde.robotcleaner.domain.space.Coordinates;
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class CleaningResult {
    /**
     * The final position the cleaner ended in.
     */
    private final Coordinates finalPosition;

    /**
     * The number of locations cleaned.
     */
    private final int locationsCleaned;

    /**
     * An out of bounds error that details the circumstances of how the cleaner went out of bounds.
     * May be null.
     */
    private final CoordinatesOutOfBoundsException outOfBoundsError;

    public CleaningResult(Coordinates finalPosition, int locationsCleaned) {
        this.finalPosition = finalPosition;
        this.locationsCleaned = locationsCleaned;
        this.outOfBoundsError = null;
    }

    public Optional<CoordinatesOutOfBoundsException> getOutOfBoundsError() {
        return Optional.ofNullable(outOfBoundsError);
    }
}