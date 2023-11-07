package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Task2 {
    private Task2() {

    }

    public static List<String> clusterize(String input) {
        if (!input.matches("[()]+")) {
            throw new IllegalArgumentException();
        }

        List<String> clusters = new ArrayList<>();
        Stack<Character> stack = new Stack<>();

        int begin = 0;
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else { //ch == ')'
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException(); //неправильный порядок скобок ")("
                }
                stack.pop();
                if (stack.isEmpty()) {
                    clusters.add(input.substring(begin, i + 1));
                    begin = i + 1;
                }
            }
        }
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException(); //несбалансированно "(())("
        }
        return clusters;
    }
}
