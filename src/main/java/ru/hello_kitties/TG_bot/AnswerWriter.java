package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.botLogic.BotResponse;

public interface AnswerWriter {
    int writeAnswer(BotResponse response);
    void deleteMessage(String chatId, int lastId);
    void editMessage(int ourId, String message, String chatId);
}
