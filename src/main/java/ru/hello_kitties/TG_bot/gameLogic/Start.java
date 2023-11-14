package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;


public class Start implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        game.setState("waiting amount players");
        return "Введите количество игроков (от 3 до 100):";
    }
}
