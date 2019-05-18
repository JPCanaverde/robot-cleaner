package pt.canaverde.robotcleaner.domain.space;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The directions a cleaner can move in.
 */
@AllArgsConstructor
public enum CardinalDirection {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W');

    private final char cardinalCharacter;

    private final static Map<Character, CardinalDirection> DIRECTIONS = new HashMap<>();

    static {
        Stream.of(CardinalDirection.values()).forEach(direction -> DIRECTIONS.put(direction.cardinalCharacter, direction));
    }

    public static Optional<CardinalDirection> fromCardinalCharacter(char cardinalCharacter) {
        return Optional.ofNullable(DIRECTIONS.get(cardinalCharacter));
    }
}
