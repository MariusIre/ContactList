import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BackupManager {

    private static final File BACKUP_DIR = new File("backups");
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final int ID_INDEX = 4;

    public BackupManager() {
        if (!BACKUP_DIR.exists()) {
            BACKUP_DIR.mkdir();
            System.out.println("Backup folder successfully created.");

        } else {
            System.out.println("Backup folder exists.");
        }
    }

    public void createBackupFile(PhoneBook phoneBook) {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            StringBuilder backupName = new StringBuilder();
            int id = Integer.parseInt(getLastBackupId());
            if (id == -1) {
                System.out.println("Creating backup failed.");
                return;
            }
            id++;

            backupName.append(BACKUP_DIR.getName()).append("/")
                    .append(format.format(date)).append("-backup-").append(id).append(".csv");
            File backup = new File(new String(backupName));
            backup.createNewFile();
            phoneBook.printTableHeader(backup.getPath());
            for (Contact contact : phoneBook.getAllCurentContacts()) {
                phoneBook.writeContactInCsv(backup.getPath(), contact);
            }
            System.out.println("Backup created: " + backup.toPath());
        } catch (IOException e) {
            System.out.println("Create backup file failed.");
        }
    }

    public String getLastBackupId() {
        if (!(BACKUP_DIR.list().length > 0)) {
            return "0";
        }
        try {
            Optional<Path> optPath = Files.list(Paths.get(BACKUP_DIR.getName()))
                    .peek(path -> System.out.println(path.getFileName()))
                    .filter(path -> path.toFile().getName().endsWith(".csv"))
                    .max(Path::compareTo);
            if (optPath.isPresent()) {
                String fileName = optPath.get().toFile().getName();
                return fileName.substring(fileName.lastIndexOf("-") + 1, fileName.lastIndexOf("."));
            }
        } catch (IOException e) {
            System.out.println("Get last backup ID exception");

        }
        return "-1";
    }

    public void viewBackupFiles() {
        Set<File> backups = readBackupFiles();
        AtomicInteger index = new AtomicInteger(1);
        backups.forEach(
                file -> {
                    try {
                        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
                        System.out.println(index + ". " +file.getName() + " Created at: " + df.format(attr.creationTime().toMillis()));
                        index.incrementAndGet();
                    } catch (IOException e) {
                        System.out.println("Atributtes error");
                    }
                }

        );
    }

    public Set<File> readBackupFiles() {
        if (!(BACKUP_DIR.length() > 0)) {
            System.out.println("No backups in folder.");
            return null;
        }
        return new TreeSet<>(Arrays.asList(BACKUP_DIR.listFiles(file -> file.getName().endsWith(".csv"))));
    }

    public void loadBackupFile() {
        Set<File> backup = readBackupFiles();

    }
}
