package ru.hello_kitties.TG_bot.console;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.logic.BotResponse;

public class ConsoleAnswerWriter implements AnswerWriter {
    public void writeAnswer(BotResponse response) {
        System.out.println("your message: "+ response.getResponse());
    }
}
