package edu.hw3.Task5;

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

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surname;
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
}
