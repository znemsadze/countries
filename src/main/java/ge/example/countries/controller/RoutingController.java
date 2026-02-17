package ge.example.countries.controller;

import ge.example.countries.service.PathFinder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    private final PathFinder pathFinder;

    public RoutingController(PathFinder routeFinder) {
        this.pathFinder = routeFinder;
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<Map<String, List<String>>> getRoute(
            @PathVariable String origin,
            @PathVariable String destination) {

        try {
            origin = origin.toUpperCase();
            destination = destination.toUpperCase();

            List<String> route = pathFinder.findRoute(origin, destination);

            if (route == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No land route found"
                );
            }

            return ResponseEntity.ok(Map.of("route", route));

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid country code: " + e.getMessage()
            );
        }
    }
}