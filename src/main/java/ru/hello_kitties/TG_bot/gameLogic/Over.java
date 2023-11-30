package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class Over implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        game.setState("/start");
        return "<i>Спасибо за игру! Если хотите сыграть ещё раз, введите /start</i>";
    }
}
