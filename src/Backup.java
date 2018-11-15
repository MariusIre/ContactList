import java.util.Date;
import java.util.Objects;

public class Backup {

    private String name;
    private Date date;
    private String location;

    public Backup(String name, Date date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Backup backup = (Backup) o;
        return Objects.equals(name, backup.name) &&
                Objects.equals(date, backup.date) &&
                Objects.equals(location, backup.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, location);
    }
}
