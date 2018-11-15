import java.io.File;

public class BackupManager {

    private static final File BACKUP_PATH = new File("backups");
    private Backup[] backups;

    public BackupManager(){
        if(!BACKUP_PATH.exists()){
            if (BACKUP_PATH.mkdir()){
                System.out.println("Folder successfully created.");
            }
        }

    }

}
