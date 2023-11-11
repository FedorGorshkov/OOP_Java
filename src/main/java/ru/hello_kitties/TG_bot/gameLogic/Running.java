package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class Running implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        request.setRequest("Здесь будет какой-то текст, связанный с временем игры");
        request.setChatId(update.getMessage().getChatId().toString());
        game.changeState("over");
    }
}
