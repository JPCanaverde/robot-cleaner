package pt.canaverde.robotcleaner.domain.cleaning;

import pt.canaverde.robotcleaner.domain.space.*;
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException;

import java.util.List;

/**
 * Replaces dirty stuff with clean stuff.
 */
public interface Cleaner {
    /**
     * Gets the current position of this cleaner.
     *
     * @return the cleaner's position in the area.
     */
    Coordinates getPosition();

    /**
     * Get the number of locations that were actually cleaned.
     *
     * @return the number of locations that had to be cleaned.
     */
    int getLocationsCleaned();

    /**
     * Cleans the specified area.
     *
     * @param area to clean.
     */
    void clean(Area area, List<CardinalDirection> directions) throws CoordinatesOutOfBoundsException;
}