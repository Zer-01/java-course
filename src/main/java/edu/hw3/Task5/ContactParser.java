package edu.hw3.Task5;

import java.util.Arrays;
import java.util.Collections;

public class ContactParser {
    private ContactParser() {

    }

    public static Contact[] parseContacts(String[] fNames, String sortOrder) {
        if (fNames == null) {
            return new Contact[]{};
        }
        Contact[] contactArr = new Contact[fNames.length];
        for (int i = 0; i < fNames.length; i++) {
            contactArr[i] = new Contact(fNames[i]);
        }

        if (sortOrder.equals("ASC")) {
            Arrays.sort(contactArr);
        } else if (sortOrder.equals("DESC")) {
            Arrays.sort(contactArr, Collections.reverseOrder());
        } else {
            throw new IllegalArgumentException();
        }

        return contactArr;
    }
}
