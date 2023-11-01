package edu.hw3.Task7;

import java.util.Comparator;

public class NullTreeComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return 0;
        } else if (str1 == null) {
            return -1;
        } else if (str2 == null) {
            return 1;
        } else {
            return str1.compareTo(str2);
        }
    }
}
