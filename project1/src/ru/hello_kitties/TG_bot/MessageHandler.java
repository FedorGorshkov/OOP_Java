package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.logic.*;

public interface MessageHandler {
    void handle(BotRequest request, AnswerWriter writer);
}
