
import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PhoneBook {

    private static final String LAST_NAME = "LAST_NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";

    private Map<String, ContactGroup> phoneBook = new TreeMap<>();
    Map<String, Integer> tableColumns = new TreeMap<>();
    String[] tableColumn;

    public PhoneBook() {
    }

    public PhoneBook(ContactGroup contactGroup) {
        for (Contact contact : contactGroup.getContactList()) {
            addContact(contact);
        }
    }

    public void addContact(Contact contact) {
        for (String key : phoneBook.keySet()) {
            if (key.equalsIgnoreCase(contact.getLastName().substring(0, 1))) {
                phoneBook.get(key).getContactList().add(contact);
                return;
            }
        }
        phoneBook.put(contact.getLastName().substring(0, 1).toUpperCase(), new ContactGroup());
        phoneBook.get(contact.getLastName().substring(0, 1).toUpperCase()).getContactList().add(contact);
    }

    public void addNewContact(Contact contact) {
        for (String key : phoneBook.keySet()) {
            if (key.equalsIgnoreCase(contact.getLastName().substring(0, 1))) {
                phoneBook.get(key).getContactList().add(contact);
                writeContactInCsv("ContactList.csv", contact);
                return;
            }
        }
        phoneBook.put(contact.getLastName().substring(0, 1).toUpperCase(), new ContactGroup());
        phoneBook.get(contact.getLastName().substring(0, 1).toUpperCase()).getContactList().add(contact);

    }

    public void removeContact(Contact removeContact) {

        if (phoneBook.get(removeContact.getFirstLetter())
                .getContactList()
                .removeIf(contact1 -> contact1.equals(removeContact))) {
            System.out.println("Contact removed.");
            removeContactFromCsv("ContactList.csv");
            return;
        }
        System.out.println("Contact remove failed.");

    }

    public void showAllContacts() {
        for (Map.Entry<String, ContactGroup> entry : phoneBook.entrySet()) {
            if (!entry.getValue().getContactList().isEmpty()) {
                System.out.println("\n" + entry.getKey());
            }
            showContactsFromCollection(entry.getValue().getContactList());
        }
        //for (String key : phoneBook.keySet()) {
        //    System.out.println("\n" + key);
        //    for (Contact contact : phoneBook.get(key).getContactList()) {
        //        System.out.println(contact);
        //    }
        //}
    }

    public void showContactsFromCollection(Collection<Contact> contacts) {
        if (contacts.isEmpty()) {
            return;
        }
        contacts.forEach(System.out::println);
    }

    public Contact findContact(Contact contact) {
        ContactGroup find = phoneBook.get(contact.getFirstLetter());
        if (find != null) {
            return find.getContactList().stream().filter(contact1 -> contact1.equals(contact)).findFirst().get();
        }
        return (Contact) Optional.empty().get();
    }

    public List<Contact> findContact(String name) {
        return phoneBook.values()
                .stream()
                .map(ContactGroup::getContactList)
                .flatMap(Collection::stream)
                .filter(contact1 -> contact1.getLastName().toLowerCase().contains(name.toLowerCase())
                        || contact1.getFirstName().contains(name))
                .collect(Collectors.toList());
    }

    private Collection<Contact> findContactsInCollection(Collection<Contact> contacts, Predicate<Contact> predicate) {
        return contacts.stream().filter(predicate).collect(Collectors.toList());
    }

    public void getContactsFromTxt(String filePath) {
        if (checkFile(filePath)) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();
            if (line.isEmpty()) {
                return;
            }
            tableColumn = line.split(",");
            for (int i = 0; i < tableColumn.length; i++) {
                switch (tableColumn[i]) {
                    case LAST_NAME:
                        tableColumns.put(LAST_NAME, i);
                        break;
                    case FIRST_NAME:
                        tableColumns.put(FIRST_NAME, i);
                        break;
                    case PHONE_NUMBER:
                        tableColumns.put(PHONE_NUMBER, i);
                        break;
                }
            }
            if (tableColumns.values().size() != 3) {
                System.out.println("CSV not coresponding.");
                return;
            }
            while ((line = reader.readLine()) != null) {

                String[] splitLine = line.split(",");
                String lastName = splitLine[tableColumns.get(LAST_NAME)];
                String firstName = splitLine[tableColumns.get(FIRST_NAME)];
                String phoneNumber = splitLine[tableColumns.get(PHONE_NUMBER)];

                addContact(new Contact(lastName, firstName, phoneNumber));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void getContactsFromList(List<Contact> contacts) {
        for (Contact contact : contacts) {
            addContact(contact);
        }
    }

    public void writeContactInCsv(String filePath, Contact contact) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))) {

            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < tableColumn.length; i++) {
                if (i == tableColumns.get(LAST_NAME)) {
                    strBuilder.append(contact.getLastName()).append(",");
                }else if (i == tableColumns.get(FIRST_NAME)) {
                    strBuilder.append(contact.getFirstName()).append(",");
                }else if (i == tableColumns.get(PHONE_NUMBER)) {
                    strBuilder.append(contact.getNumber());
                } else {
                    strBuilder.append(" ,");
                }
            }
            String s = new String(strBuilder);
            bw.newLine();
            bw.write(s);
            bw.close();

        } catch (IOException e) {
            System.out.println("File not found.");
        }

    }

    public void removeContactFromCsv(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < tableColumn.length; i++) {
                if (i == tableColumns.get(LAST_NAME)) {
                    strBuilder.append(LAST_NAME).append(",");
                }else if (i == tableColumns.get(FIRST_NAME)) {
                    strBuilder.append(FIRST_NAME).append(",");
                }else if (i == tableColumns.get(PHONE_NUMBER)) {
                    strBuilder.append(PHONE_NUMBER);
                } else {
                    strBuilder.append(" ,");
                }
            }
            String s = new String(strBuilder);
            bw.write(s);
            bw.close();
            for(String key : phoneBook.keySet()) {
                for(Contact contact : phoneBook.get(key).getContactList()) {
                    writeContactInCsv(filePath,contact);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

    }

    public void editContact(Contact contact, Contact editContact){
        removeContact(contact);
        addContact(editContact);
    }

    private boolean checkFile(String filePath) {
        boolean isEmpty = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            isEmpty = reader.readLine() == null;

        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return isEmpty;
    }

}
