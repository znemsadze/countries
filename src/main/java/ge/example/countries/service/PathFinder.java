package ge.example.countries.service;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathFinder {

    private final CountryDataLoader countryDataLoader;

    public PathFinder(CountryDataLoader countryDataLoader) {
        this.countryDataLoader = countryDataLoader;
    }

    public List<String> findRoute(String origin, String destination) {
        if (!countryDataLoader.countryExists(origin) ||
                !countryDataLoader.countryExists(destination)) {
            throw new IllegalArgumentException("Invalid country code");
        }
        if (origin.equals(destination)) {
            return List.of(origin);
        }
        // BFS
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        queue.add(origin);
        visited.add(origin);
        parentMap.put(origin, null);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : countryDataLoader.getBorders(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    if (neighbor.equals(destination)) {
                        return reconstructPath(parentMap,  destination);
                    }
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    private List<String> reconstructPath(Map<String, String> parentMap,
                                          String destination) {
        LinkedList<String> path = new LinkedList<>();
        String current = destination;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }
}
