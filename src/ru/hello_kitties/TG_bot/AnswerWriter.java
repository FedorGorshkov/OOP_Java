package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.logic.BotResponse;

public interface AnswerWriter {
    void writeAnswer(BotResponse response);
}
