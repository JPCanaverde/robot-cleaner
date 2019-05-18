package pt.canaverde.robotcleaner.domain.space;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Area {
    private final Map<Coordinates, Location> area = new HashMap<>();

    /**
     * Initialises an space with a set width and height.
     *
     * It is not possible to add locations outside these bounds once the space has been initialised.
     *
     * @param width
     * @param height
     * @param initialSurface of locations in the space.
     */
    public Area(int width, int height, Surface initialSurface) {
        if (initialSurface == null) {
            throw new NullPointerException("Please specify an initial surface.");
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var coordinates = new Coordinates(x, y);

                this.area.put(coordinates, new Location(coordinates, initialSurface));
            }
        }
    }

    /**
     * Checks if the coordinates given are valid for this area.
     *
     * @param position to check.
     * @return true if the location exists in the area, false otherwise.
     */
    public boolean locationExists(Coordinates position) {
        return this.getLocation(position).isPresent();
    }

    /**
     * Gets the space present in the given coordinates.
     *
     * @param coordinates
     * @return the space if it exists, empty() if not.
     */
    public Optional<Location> getLocation(Coordinates coordinates) {
        return Optional.ofNullable(area.get(coordinates));
    }

    /**
     * Updates a given space using its coordinates.
     * Returns the space previously present in the given coordinates.
     *
     * @param location to update in the space. Should include coordinates.
     * @return the previous space if it exists, empty if not.
     */
    public Optional<Location> updateLocation(Location location) {
        var previousLocation = this.area.get(location.getCoordinates());

        if (previousLocation == null) {
            return Optional.empty();
        }

        this.area.put(location.getCoordinates(), location);

        return Optional.of(previousLocation);
    }
}
