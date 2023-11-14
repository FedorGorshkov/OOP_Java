package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.botLogic.*;
import ru.hello_kitties.TG_bot.telegram.Bot;

public interface MessageHandler {
    void handle(Bot bot, Game game, String text, String chatId);
}
