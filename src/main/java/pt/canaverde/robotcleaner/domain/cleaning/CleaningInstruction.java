package pt.canaverde.robotcleaner.domain.cleaning;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.canaverde.robotcleaner.domain.space.Area;
import pt.canaverde.robotcleaner.domain.space.CardinalDirection;
import pt.canaverde.robotcleaner.domain.space.Coordinates;

import java.util.List;

@Getter
@AllArgsConstructor
public class CleaningInstruction {
    /**
     * The area to clean.
     */
    private final Area area;

    /**
     * The initial position the cleaner will be in.
     */
    private final Coordinates initialPosition;

    /**
     * The directions for the cleaner to follow.
     */
    private final List<CardinalDirection> directions;
}
