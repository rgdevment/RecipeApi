package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import java.util.Map;

public class CountryResponse {
    private String commonName;
    private Map<String, String> flags;

    // Getters and setters
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, String> flags) {
        this.flags = flags;
    }
}