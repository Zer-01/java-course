package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {
    private Task6() {
    }

    public static boolean isSubSequence(String s, String t) {
        StringBuilder newS = new StringBuilder(".*");
        for (char ch : s.toCharArray()) {
            newS.append(ch).append(".*");
        }
        Pattern pattern = Pattern.compile(newS.toString());
        Matcher matcher = pattern.matcher(t);
        return matcher.find();
    }
}
