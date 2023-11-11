package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.botLogic.BotResponse;

public interface AnswerWriter {
    void writeAnswer(BotResponse response);
}
