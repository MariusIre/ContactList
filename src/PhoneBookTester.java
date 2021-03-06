import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PhoneBookTester {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
/*        test.addContact(new Contact("Vasile", "Vasilescu", "0719.19.19.98"));
        test.addContact(new Contact("Ana", "Anescu", "0728.28.28.22"));
        test.addContact(new Contact("Ion", "Ionescu", "0737.37.37.98"));
        test.addContact(new Contact("Gigi", "Gigescu", "0746.46.46.98"));
        test.addContact(new Contact("Tony", "Tonescu", "0755.55.55.98"));
        test.addContact(new Contact("Mircea", "Mircescu", "0764.64.64.98"));
        test.addContact(new Contact("Cristi", "Cristescu", "0773.73.73.98"));
        test.addContact(new Contact("Bogdan", "Bogdanescu", "0782.82.82.98"));
        test.addContact(new Contact("Adrian", "Adriescu", "0791.91.91.98"));*/
        PhoneBook phoneBook = new PhoneBook();
        BackupManager manager = new BackupManager();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future loadContacts =
                executorService.submit(()->phoneBook.getContactsFromTxt("ContactList.csv"));


        menu(scan, phoneBook, manager,loadContacts);

        phoneBook.showAllContacts();


    }

    private static void menu(Scanner scan, PhoneBook phoneBook, BackupManager manager,Future future) {
        System.out.println("\n|-----------------------------------------------|");
        System.out.println("|                      MENU                     |");
        System.out.println("|-----------------------------------------------|");
        System.out.println("1.Search contact.");
        System.out.println("2.Edit contact.");
        System.out.println("3.Add contact.");
        System.out.println("4.Remove contact.");
        System.out.println("5.Backups.");
        answerMenu(scan, phoneBook, manager,future);
    }

    private static void answerMenu(Scanner scan, PhoneBook phoneBook, BackupManager manager,Future future) {
        String answer = scan.nextLine().toLowerCase();
        switch (answer) {
            case "1":
            case "search":
                searchContact(phoneBook, scan, manager , future);
                break;
            case "2":
            case "edit":
                phoneBook.editContact(createContact(scan), createContact(scan));
                break;
            case "3":
            case "add":
                phoneBook.addNewContact(createContact(scan));
                break;
            case "4":
            case "remove":
                phoneBook.removeContact(createContact(scan));
                break;
            case "5":
            case "backups":
                backupMenu(scan, phoneBook, manager,future);
                break;
            default:
                System.out.println("Wrong input, try again.");
                answerMenu(scan, phoneBook, manager,future);
        }
    }

    private static void searchContact(PhoneBook phoneBook, Scanner scan, BackupManager manager,Future future) {
        try {
            future.get();
        }catch (Exception e) {
            System.out.println("Future get failed.");
        }
        System.out.println("Insert string:");
        String answer = scan.nextLine();
        if (!answer.isEmpty()) {
            phoneBook.showContactsFromCollection(phoneBook.findContact(answer));
            answerSearchContact(phoneBook, scan, manager,future);
            return;
        }
        System.out.println("Nothing specified, try again.");
        searchContact(phoneBook, scan, manager,future);


    }

    private static void answerSearchContact(PhoneBook phoneBook, Scanner scan, BackupManager manager, Future future) {
        System.out.println("1.Search again");
        System.out.println("2.Menu.");
        String answer = scan.nextLine();
        switch (answer) {
            case "1":
            case "search":
                searchContact(phoneBook, scan, manager,future);
                break;
            case "2":
            case "menu":
                menu(scan, phoneBook, manager,future);
                break;
            default:
                System.out.println("Incorect input, try again.");
                answerSearchContact(phoneBook, scan, manager,future);
        }
    }

    private static Contact createContact(Scanner scan) {
        System.out.println("Last name:");
        String lastName = scan.nextLine();
        System.out.println("First name:");
        String firstName = scan.nextLine();
        System.out.println("Phone number:");
        String phoneNumber = scan.nextLine();
        return new Contact(lastName, firstName, phoneNumber);
    }

    private static void backupMenu(Scanner scan, PhoneBook phoneBook, BackupManager manager,Future future) {
        System.out.println("1.Create backup.");
        System.out.println("2.View backup.");
        System.out.println("3.Load backup.");
        System.out.println("4.Delete backup.");
        System.out.println("5.Main menu.");
        answerBackupMenu(scan, phoneBook, manager,future);
    }

    private static void answerBackupMenu(Scanner scan, PhoneBook phoneBook, BackupManager manager, Future future) {

        String answer = scan.nextLine().toLowerCase();
        switch (answer) {
            case "1":
            case "create":
                manager.createBackupFile(phoneBook);
                break;
            case "2":
            case "view":
                manager.viewBackupFiles();
                break;
            case "3":
            case "load":

                break;
            case "4":
            case "remove":
                break;
            case "5":
            case "menu":
                menu(scan, phoneBook, manager,future);
                break;
            default:
                System.out.println("Incorrect input, try again.");
                answerBackupMenu(scan, phoneBook, manager,future);
        }
    }
}
