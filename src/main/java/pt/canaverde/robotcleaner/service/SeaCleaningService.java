package pt.canaverde.robotcleaner.service;

import org.springframework.stereotype.Service;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningInstruction;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningResult;
import pt.canaverde.robotcleaner.domain.cleaning.SeaCleaner;
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException;

@Service
public class SeaCleaningService implements CleaningService {
    @Override
    public CleaningResult clean(CleaningInstruction cleaningInstruction) {
        var cleaner = new SeaCleaner(cleaningInstruction.getInitialPosition());

        try {
            cleaner.clean(cleaningInstruction.getArea(), cleaningInstruction.getDirections());
            return new CleaningResult(cleaner.getPosition(), cleaner.getLocationsCleaned());
        } catch (CoordinatesOutOfBoundsException outOfBoundsError) {
            return new CleaningResult(cleaner.getPosition(), cleaner.getLocationsCleaned(), outOfBoundsError);
        }
    }
}
