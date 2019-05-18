package pt.canaverde.robotcleaner.presentation.model.sea;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import pt.canaverde.robotcleaner.domain.cleaning.CleaningInstruction;
import pt.canaverde.robotcleaner.domain.space.*;
import pt.canaverde.robotcleaner.exceptions.InvalidCardinalDirectionException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Describes the area to be cleaned and the navigation instructions for a cleaner.
 */
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = SeaCleaningInstruction.SeaCleaningInstructionBuilder.class)
public class SeaCleaningInstruction {
    /**
     * Length and height of the area.
     * [rows, columns]
     */
    @Size(min = 2, max = 2)
    private final int[] areaSize;

    /**
     * The position where the cleaner should start, in [X, Y] form.
     */
    @Size(min = 2, max = 2)
    private final int[] startingPosition;

    /**
     * The list of positions of oil patches in [X,Y] form.
     */
    @NotNull
    private final List<int[]> oilPatches;

    /**
     * The navigation instructions in the form of cardinal characters.
     *
     * N
     * E
     * S
     * W
     */
    @NotNull
    private final String navigationInstructions;

    /**
     * Converts this API Sea Cleaning Instruction into a domain Cleaning Instruction.
     *
     * @return the converted cleaning instruction.
     * @throws InvalidCardinalDirectionException if any of the cardinal directions are invalid.
     */
    public CleaningInstruction toCleaningInstruction() throws InvalidCardinalDirectionException {
        var area = new Area(areaSize[0], areaSize[1], Surface.SEA);
        var initialPosition = Coordinates.fromArray(startingPosition);

        oilPatches.forEach(patch -> {
            if (patch.length != 2) {
                throw new SeaApiError("Coordinates of oil patches do not have a valid length.");
            }

            var location = new Location(Coordinates.fromArray(patch), Surface.OIL);

            Optional<Location> previousLocation = area.updateLocation(location);

            if (previousLocation.isEmpty()) {
                throw new SeaApiError("Instruction contains an oil patch in a non-existing position.");
            }
        });

        List<CardinalDirection> directions = navigationInstructions.replaceAll("\\s+", "")
                .toUpperCase().chars()
                .mapToObj(i -> (char) i)
                .map(CardinalDirection::fromCardinalCharacter)
                .map(cardinalDirection -> {
                    if (cardinalDirection.isEmpty()) {
                        throw new InvalidCardinalDirectionException();
                    }

                    return cardinalDirection.get();
                })
                .collect(Collectors.toList());

        return new CleaningInstruction(area, initialPosition, directions);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class SeaCleaningInstructionBuilder {

    }
}
