package ge.example.countries.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private Name name;
    private List<String> borders;
    private String cca3;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String common;
        private String official;

    }
}
