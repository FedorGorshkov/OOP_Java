package ru.hello_kitties.TG_bot;
import ru.hello_kitties.TG_bot.logic.BotRequest;
public interface InputReader {
     BotRequest getUserInput(BotRequest request);
}
