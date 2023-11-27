package edu.hw8.Task1;

import java.util.Map;

public class AnsweringMachine {
    private static final Map<String, String> ANSWERS = Map.ofEntries(
        Map.entry("личности", "Не переходи на личности там, где их нет"),
        Map.entry(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
        ),
        Map.entry(
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
        ),
        Map.entry("интеллект", "Чем ниже интеллект, тем громче оскорбления")
    );

    private AnsweringMachine() {
    }

    public static String getAnswer(String input) {
        return ANSWERS.getOrDefault(input, "¯\\_(ツ)_/¯");
    }
}
