import java.util.Objects;

public class PhoneNumber implements Comparable<PhoneNumber>{

    private String number;
    private CountryType country;

    public PhoneNumber(String number) {
        this.number = number;
        this.country = CountryType.getCountryByCode(number);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CountryType getCountry() {
        return country;
    }

    @Override
    public int compareTo(PhoneNumber phoneNumber) {
        if(number.compareToIgnoreCase(phoneNumber.number) != 0){
            return number.compareToIgnoreCase(phoneNumber.number);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number) &&
                country == that.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, country);
    }
}
