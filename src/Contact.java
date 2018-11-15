import java.util.Objects;

public class Contact implements Comparable<Contact> {

    private String lastName;
    private String firstName;
    private PhoneNumber number;

    public Contact() {}

    public Contact(String lastName){
        this.lastName = lastName;
    }

    public Contact(String lastName, String firstName, String number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = new PhoneNumber(number);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number.getNumber();
    }

    public void setNumber(String number) {
        this.number = new PhoneNumber(number);
    }

    public String getFirstLetter () {
        return lastName.substring(0,1).toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, number);
    }

    @Override
    public int compareTo(Contact contact) {
        if (lastName.compareToIgnoreCase(contact.lastName) != 0) {
            return lastName.compareToIgnoreCase(contact.lastName);
        }
        if (firstName.compareToIgnoreCase(contact.firstName) != 0) {
            return firstName.compareToIgnoreCase(contact.firstName);
        }
        if (number.compareTo(contact.number) != 0) {
            return number.compareTo(contact.number);
        }
        return 0;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + number.getNumber();
    }
}
