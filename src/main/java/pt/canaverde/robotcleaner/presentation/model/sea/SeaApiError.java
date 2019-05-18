package pt.canaverde.robotcleaner.presentation.model.sea;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import pt.canaverde.robotcleaner.presentation.error.ApiError;
import pt.canaverde.robotcleaner.presentation.error.CleaningResultError;

import java.util.Optional;

@JsonDeserialize(builder = SeaApiError.SeaApiErrorBuilder.class)
public class SeaApiError extends ApiError {
    /**
     * The cleaning results up to the point where this error occurred.
     */
    private final SeaCleaningResult partialResult;

    public Optional<SeaCleaningResult> getPartialResult() {
        return Optional.ofNullable(partialResult);
    }

    public SeaApiError(String message) {
        super(message);
        this.partialResult = null;
    }

    public SeaApiError(String message, CleaningResultError error, SeaCleaningResult partialResult) {
        super(message, error);
        this.partialResult = partialResult;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class SeaApiErrorBuilder extends ApiErrorBuilder {
        private SeaCleaningResult partialResult;

        public SeaApiErrorBuilder partialResult(SeaCleaningResult partialResult) {
            this.partialResult = partialResult;
            return this;
        }

        @Override
        public SeaApiError build() {
            ApiError error = super.build();

            return error.getDetails().map(details -> new SeaApiError(error.getMessage(), details, this.partialResult))
                    .orElse(new SeaApiError(error.getMessage()));
        }
    }
}
