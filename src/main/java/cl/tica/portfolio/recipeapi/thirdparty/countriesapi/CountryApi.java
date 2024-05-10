package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import java.util.Map;

public class CountryApi {
    private Map<String, String> commonName;
    private Map<String, String> flags;

    // Getters and setters
    public Map<String, String> getCommonName() {
        return commonName;
    }

    public void setName(Map<String, String> name) {
        this.commonName = name;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, String> flags) {
        this.flags = flags;
    }
}
