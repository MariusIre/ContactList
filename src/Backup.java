
import java.io.File;
import java.util.Objects;

public class Backup implements Comparable<Backup>{

    private File file;

    public Backup(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int compareTo(Backup b) {
        return file.getName().compareTo(b.getFile().getName());
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Backup backup = (Backup) o;
        return Objects.equals(file, backup.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
