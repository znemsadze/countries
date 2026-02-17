package ge.example.countries.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.example.countries.model.Country;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CountryDataLoader {

    private static final String COUNTRIES_URL =
            "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";

    private final Map<String, Country> countryMap = new ConcurrentHashMap<>();
    private final Map<String, List<String>> borderGraph = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadCountries() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Country> countries = mapper.readValue(
                new URL(COUNTRIES_URL),
                new TypeReference<List<Country>>() {}
        );
        countries.forEach(country -> countryMap.put(country.getCca3(), country));
        countries.forEach(country -> {
            if (country.getBorders() != null) {
                borderGraph.put(country.getCca3(), country.getBorders());
            }
        });
    }

    public boolean countryExists(String cca3) {
        return countryMap.containsKey(cca3);
    }

    public List<String> getBorders(String cca3) {
        return borderGraph.getOrDefault(cca3, List.of());
    }
}
