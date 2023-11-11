package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;
import org.telegram.telegrambots.meta.api.objects.Update;


public class Start implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        request.setRequest(update.getMessage().getText());
        request.setChatId(update.getMessage().getChatId().toString());
        game.changeState("waiting amount players");
    }
}
