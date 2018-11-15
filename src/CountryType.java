public enum CountryType {

    ROMANIA("+40", "Romania"),
    FRACE("+33", "France"),
    ITALY("+39", "Italy"),
    GERMANY("+49","Germany");

    private String name;
    private String code;

    CountryType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static CountryType getCountryByName(String name) {
        for (CountryType country : CountryType.values()) {
            if (country.name.equals(name)) {
                return country;
            }
        }
        return null;
    }

    public static CountryType getCountryByCode(String code) {
        for (CountryType country : CountryType.values()) {
            if (country.code.equals(code)) {
                return country;
            }
        }
        return null;
    }
}
