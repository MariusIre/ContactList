import java.util.Set;
import java.util.TreeSet;

public class ContactGroup {

    private Set<Contact> contactList;

    public ContactGroup() {
        contactList = new TreeSet<>();
    }

    public Set<Contact> getContactList() {
        return contactList;
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
    }



}
