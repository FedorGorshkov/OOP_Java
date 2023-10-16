package ru.hello_kitties.TG_bot.telegram;

import jdk.javadoc.internal.tool.Main;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.logic.BotResponse;

import static jdk.javadoc.internal.tool.Main.execute;

public class AnswerWriterImplements implements AnswerWriter {

    @Override
    public void writeAnswer(BotResponse response) {
        Main.execute(response.getResponse());
    }
}
