package pt.canaverde.robotcleaner.presentation.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

/**
 * An API error that may be returned to the client if their instructions are illegal.
 */
@Builder
@JsonDeserialize(builder = ApiError.ApiErrorBuilder.class)
public class ApiError extends RuntimeException {
    /**
     * Human readable message about the error that occurred.
     */
    private final String message;

    /**
     * Details about the error, like where it occurred.
     */
    private final CleaningResultError details;

    public Optional<CleaningResultError> getDetails() {
        return Optional.ofNullable(this.details);
    }

    public ApiError(String message) {
        super(message);
        this.message = message;
        this.details = null;
    }

    public ApiError(String message, CleaningResultError details) {
        super(message);
        this.message = message;
        this.details = details;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ApiErrorBuilder {
        public ApiErrorBuilder() {
        }

        private String message;
        private CleaningResultError details;

        ApiErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        ApiErrorBuilder details(CleaningResultError details) {
            this.details = details;
            return this;
        }

        public ApiError build() {
            return new ApiError(this.message, this.details);
        }
    }
}
