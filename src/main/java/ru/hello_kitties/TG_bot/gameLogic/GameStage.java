package ru.hello_kitties.TG_bot.gameLogic;
import ru.hello_kitties.TG_bot.botLogic.Game;

public interface GameStage {
    String processMsg(Game game, String message);
}
