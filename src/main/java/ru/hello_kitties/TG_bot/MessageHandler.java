package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.botLogic.*;

public interface MessageHandler {
    void handle(BotRequest request, AnswerWriter writer);
}
