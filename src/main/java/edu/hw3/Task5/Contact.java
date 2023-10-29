package edu.hw3.Task5;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Contact implements Comparable<Contact> {
    private final String name;
    private final String surname;

    public Contact(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length == 2) {
            name = parts[0];
            surname = parts[1];
        } else if (parts.length == 1) {
            name = parts[0];
            surname = null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return (surname == null) ? name : name + " " + surname;
    }

    @Override
    public int compareTo(@NotNull Contact contact) {
        String compString1 = (surname == null) ? name : surname;
        String compString2 = (contact.surname == null) ? contact.name : contact.surname;
        return compString1.compareTo(compString2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Contact guest = (Contact) obj;
        return Objects.equals(name, guest.name) && Objects.equals(surname, guest.surname);
    }

    @Override
    public int hashCode() {
        final int hashBase = 31;
        int result = hashBase;
        result = result * hashBase + ((name == null) ? 0 : name.hashCode());
        result = result * hashBase + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }
}
