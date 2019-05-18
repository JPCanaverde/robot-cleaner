package pt.canaverde.robotcleaner.domain.space

import spock.lang.Specification

class AreaSpec extends Specification {
    def "area requires an initial surface"() {
        when:
        def area = new Area(1, 1, null)

        then:
        thrown(NullPointerException)
    }

    def "area is initialised correctly"() {
        when:
        def size = 5
        def area = new Area(size, size, Surface.SEA)

        then:
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                assert area.locationExists(new Coordinates(x, y))
                assert area.getLocation(new Coordinates(x, y)).get().getSurface() == Surface.SEA
            }
        }

        !area.locationExists(new Coordinates(-1, 0))
        !area.locationExists(new Coordinates(0, -1))
        !area.locationExists(new Coordinates(-1, -1))
        !area.locationExists(new Coordinates(0, 5))
        !area.locationExists(new Coordinates(5, 0))
        !area.locationExists(new Coordinates(5, 5))
    }

    def "locations are updated correctly"() {
        when:
        def size = 5
        def area = new Area(size, size, Surface.SEA)

        then:
        area.getLocation(new Coordinates(1, 1)).get().getSurface() == Surface.SEA

        when:
        area.updateLocation(new Location(new Coordinates(1, 1), Surface.OIL))

        then:
        area.getLocation(new Coordinates(1, 1)).get().getSurface() == Surface.OIL
    }
}
