package pt.canaverde.robotcleaner.exceptions;

/**
 * Thrown if there's an attempted instantiation of an invalid cardinal direction.
 */
public class InvalidCardinalDirectionException extends RuntimeException {
    public InvalidCardinalDirectionException() {
        super("The only valid cardinal directions are: N, E, S, W");
    }
}