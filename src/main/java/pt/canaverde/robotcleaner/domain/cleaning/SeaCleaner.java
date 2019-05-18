package pt.canaverde.robotcleaner.domain.cleaning;

import pt.canaverde.robotcleaner.domain.space.Coordinates;
import pt.canaverde.robotcleaner.domain.space.Surface;

/**
 * Not to be confused with CCleaner.
 *
 * Replaces oil with sea.
 */
public class SeaCleaner extends SimpleCleaner {
    public SeaCleaner(Coordinates position) {
        super(position);
    }

    @Override
    public Surface getCleanSurface() {
        return Surface.SEA;
    }

    @Override
    public Surface getDirtySurface() {
        return Surface.OIL;
    }
}
