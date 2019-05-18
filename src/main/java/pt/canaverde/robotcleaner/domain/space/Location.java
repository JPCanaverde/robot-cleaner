package pt.canaverde.robotcleaner.domain.space;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

@Getter
@Wither
@AllArgsConstructor
public class Location {
    /**
     * The coordinates this location is in.
     */
    private final Coordinates coordinates;

    /**
     * The surface type of this location.
     */
    private final Surface surface;
}