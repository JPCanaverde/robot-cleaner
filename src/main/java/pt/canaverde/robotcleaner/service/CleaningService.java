package pt.canaverde.robotcleaner.service;

import pt.canaverde.robotcleaner.domain.cleaning.CleaningInstruction;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningResult;

public interface CleaningService {
    /**]
     * Carries out the process of cleaning from an instruction and returns the result.
     *
     * @param cleaningInstruction to follow.
     * @return the results of the cleaning process.
     */
    CleaningResult clean(CleaningInstruction cleaningInstruction);
}