package ru.hello_kitties.TG_bot;

import ru.hello_kitties.TG_bot.console.ConsoleAnswerWriter;
import ru.hello_kitties.TG_bot.console.ConsoleInputReader;
import ru.hello_kitties.TG_bot.logic.*;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new ConsoleInputReader();
        AnswerWriter writer = new ConsoleAnswerWriter();
        Handler handler = new Handler();

        while (true) {
            BotRequest request = reader.getUserInput();
            handler.handle(request, writer);
        }
    }
}
