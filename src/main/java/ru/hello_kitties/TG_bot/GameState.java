package ru.hello_kitties.TG_bot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;
import ru.hello_kitties.TG_bot.botLogic.Handler;

public interface GameState {
    void makeGame(Game game, BotRequest request, Update update);
}
