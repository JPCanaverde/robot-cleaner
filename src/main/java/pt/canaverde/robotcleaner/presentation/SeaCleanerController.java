package pt.canaverde.robotcleaner.presentation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningResult;
import pt.canaverde.robotcleaner.exceptions.InvalidCardinalDirectionException;
import pt.canaverde.robotcleaner.presentation.error.ApiError;
import pt.canaverde.robotcleaner.presentation.model.sea.SeaApiError;
import pt.canaverde.robotcleaner.presentation.model.sea.SeaCleaningInstruction;
import pt.canaverde.robotcleaner.presentation.model.sea.SeaCleaningResult;
import pt.canaverde.robotcleaner.service.CleaningService;
import pt.canaverde.robotcleaner.service.SeaCleaningService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class SeaCleanerController {
    private final CleaningService service;

    @PostMapping("/sea/clean")
    SeaCleaningResult clean(@Valid @RequestBody SeaCleaningInstruction seaCleaningInstruction) {
        CleaningResult result = service.clean(seaCleaningInstruction.toCleaningInstruction());

        return SeaCleaningResult.from(result);
    }

    @ControllerAdvice
    public static class Advice {
        @ResponseBody
        @ExceptionHandler(InvalidCardinalDirectionException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        ApiError invalidCardinalDirection(InvalidCardinalDirectionException directionException) {
            return new ApiError(directionException.getMessage());
        }

        @ResponseBody
        @ExceptionHandler(SeaApiError.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        SeaApiError invalidCardinalDirection(SeaApiError error) {
            return error;
        }
    }
}
