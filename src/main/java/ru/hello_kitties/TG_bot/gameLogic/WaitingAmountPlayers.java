package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class WaitingAmountPlayers implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        game.setNumberOfPlayers(Integer.parseInt(message));
        game.setState("entering names");
        return "Вводите имена игроков (можно по одному, либо по несколько сразу через запятую с пробелом):";
    }
}
