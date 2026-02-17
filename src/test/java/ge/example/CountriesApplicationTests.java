package ge.example;

import ge.example.countries.CountriesApplication;
import ge.example.countries.service.PathFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootConfiguration
@SpringBootTest(classes = CountriesApplication.class)
class CountriesApplicationTests {

    @Autowired
    PathFinder pathFinder;

    @Test
    void contextLoads() {
        List<String> result = pathFinder.findRoute("CZE", "AUT");
        Assert.notEmpty(result, "No route found");
        Assert.isTrue(result.size() == 2, "Route should have two elements");
    }
}
