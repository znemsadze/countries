package ge.example;

import ge.example.countries.service.CountryDataLoader;
import ge.example.countries.service.PathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PathFinderTest {

    @Mock
    private CountryDataLoader countryDataLoader;

    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        pathFinder = new PathFinder(countryDataLoader);
    }

    @Test
    void findRoute_sameOriginAndDestination_returnsSingleElementList() {
        stubCountryExists("CZE", true);

        List<String> route = pathFinder.findRoute("CZE", "CZE");

        assertEquals(List.of("CZE"), route);
    }

    @Test
    void findRoute_directNeighbors_returnsTwoElementPath() {
        stubCountryExists("CZE", true);
        stubCountryExists("AUT", true);
        stubBorders("CZE", List.of("AUT", "DEU", "POL", "SVK"));

        List<String> route = pathFinder.findRoute("CZE", "AUT");

        assertEquals(List.of("CZE", "AUT"), route);
    }


    @Test
    void findRoute_noPathExists_returnsNull() {
        stubCountryExists("USA", true);
        stubCountryExists("AUS", true);
        stubBorders("USA", List.of("CAN", "MEX"));
        stubBorders("CAN", List.of("USA"));
        stubBorders("MEX", List.of("USA", "GTM"));
        stubBorders("GTM", List.of("MEX"));

        List<String> route = pathFinder.findRoute("USA", "AUS");

        assertNull(route);
    }

    @Test
    void findRoute_invalidOrigin_throwsIllegalArgumentException() {
        stubCountryExists("XXX", false);

        assertThrows(IllegalArgumentException.class,
                () -> pathFinder.findRoute("XXX", "CZE"));
    }

    @Test
    void findRoute_invalidDestination_throwsIllegalArgumentException() {
        stubCountryExists("CZE", true);
        stubCountryExists("XXX", false);

        assertThrows(IllegalArgumentException.class,
                () -> pathFinder.findRoute("CZE", "XXX"));
    }

    @Test
    void findRoute_bothInvalid_throwsIllegalArgumentException() {
        stubCountryExists("AAA", false);

        assertThrows(IllegalArgumentException.class,
                () -> pathFinder.findRoute("AAA", "BBB"));
    }

    @Test
    void findRoute_countryWithNoBorders_returnsNull() {
        stubCountryExists("ISL", true);
        stubCountryExists("DEU", true);
        stubBorders("ISL", List.of());

        List<String> route = pathFinder.findRoute("ISL", "DEU");

        assertNull(route);
    }


    private void stubCountryExists(String countryCode, boolean exists) {
        when(countryDataLoader.countryExists(countryCode)).thenReturn(exists);
    }

    private void stubBorders(String countryCode, List<String> borders) {
        when(countryDataLoader.getBorders(countryCode)).thenReturn(borders);
    }
}