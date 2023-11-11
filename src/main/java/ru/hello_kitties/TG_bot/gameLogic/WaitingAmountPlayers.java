package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;
import ru.hello_kitties.TG_bot.botLogic.Handler;

public class WaitingAmountPlayers implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        game.setNumberOfPlayers(Integer.parseInt(update.getMessage().getText()));
        request.setRequest("Вводите имена игроков (можно по одному, либо по несколько сразу через запятую с пробелом):");
        request.setChatId(update.getMessage().getChatId().toString());
        game.changeState("entering names");
    }
}
