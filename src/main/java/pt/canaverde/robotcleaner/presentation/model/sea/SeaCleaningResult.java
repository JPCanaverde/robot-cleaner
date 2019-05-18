package pt.canaverde.robotcleaner.presentation.model.sea;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningResult;
import pt.canaverde.robotcleaner.presentation.error.CleaningResultError;

/**
 *
 */
@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = SeaCleaningResult.SeaCleaningResultBuilder.class)
public class SeaCleaningResult {
    /**
     * The final position the cleaner ended in, in [X, Y] form.
     */
    private final int[] finalPosition;

    /**
     * The number of oil patches cleaned.
     */
    private final int oilPatchesCleaned;

    /**
     * Transforms a cleaning result into an API SeaCleaningResult.
     *
     * @param cleaningResult to be converted.
     * @return the converted SeaCleaningResult.
     */
    public static SeaCleaningResult from(CleaningResult cleaningResult) {
        var finalPosition = cleaningResult.getFinalPosition().toArray();
        var oilPatchesCleaned = cleaningResult.getLocationsCleaned();

        var seaCleaningResult = new SeaCleaningResult(finalPosition, oilPatchesCleaned);
        var error = cleaningResult.getOutOfBoundsError();

        if (error.isPresent()) {
            throw new SeaApiError(
                    "The sea cleaner went out of bounds.",
                    CleaningResultError.from(error.get()),
                    seaCleaningResult
            );
        }

        return seaCleaningResult;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class SeaCleaningResultBuilder {

    }
}