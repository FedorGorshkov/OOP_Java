package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class Over implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        request.setRequest("Спасибо за игру! Если хотите сыграть ещё раз, введите /start");
        request.setChatId(update.getMessage().getChatId().toString());
        game.changeState("/start");
    }
}
