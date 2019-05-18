package pt.canaverde.robotcleaner.domain.cleaning;

import lombok.Getter;
import pt.canaverde.robotcleaner.domain.space.*;
import pt.canaverde.robotcleaner.exceptions.CoordinatesOutOfBoundsException;

import java.util.List;

/**
 * A simple cleaner that replaces dirty stuff with clean stuff.
 *
 * Also keeps track of its position and the number of locations cleaned.
 */
@Getter
public abstract class SimpleCleaner implements Cleaner {
    private Coordinates position;
    private int locationsCleaned = 0;

    public SimpleCleaner(Coordinates position) {
        this.position = position;
    }

    @Override
    public void clean(Area area, List<CardinalDirection> directions) throws CoordinatesOutOfBoundsException {
        if (!area.locationExists(this.position)) {
            throw new CoordinatesOutOfBoundsException(this.position, this.position);
        }

        this.cleanAtCurrentPosition(area);

        for (int i = 0; i < directions.size(); i++) {
            try {
                this.move(area, directions.get(i));
            } catch (CoordinatesOutOfBoundsException outOfBoundsException) {
                throw outOfBoundsException.withIndexOfInvalidCardinalDirection(i);
            }
        }
    }

    /**
     * @return the type that should be considered to be "clean".
     */
    protected abstract Surface getCleanSurface();

    /**
     *
     * @return the type that should be considered to be "dirty".
     */
    protected abstract Surface getDirtySurface();

    /**
     * Moves the cleaner in the specified direction.
     * The position it ends in will be cleaned if necessary.
     *
     * @param area the cleaner is in.
     * @param direction to move in.
     * @throws CoordinatesOutOfBoundsException if the move will cause the cleaner to go outside its area.
     */
    private void move(Area area, CardinalDirection direction) throws CoordinatesOutOfBoundsException {
        var adjacentPosition = this.position.getAdjacentPosition(direction);

        if (!area.locationExists(adjacentPosition)) {
            throw new CoordinatesOutOfBoundsException(this.position, adjacentPosition);
        }

        this.position = adjacentPosition;
        this.cleanAtCurrentPosition(area);
    }

    /**
     * If the surface of the location at the current position is dirty, update it with a clean surface.
     *
     * @param area we're cleaning.
     */
    private void cleanAtCurrentPosition(Area area) {
        var location = area.getLocation(this.getPosition()).get();

        if (location.getSurface() == getDirtySurface()) {
            this.locationsCleaned++;
            area.updateLocation(location.withSurface(getCleanSurface()));
        }
    }
}