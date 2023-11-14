package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class Running implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // TODO: сделать таймер игры и досрочное завершение, если все шпионы раскрыты
        game.setState("over");
        return "Здесь будет какой-то текст, связанный с временем игры";
    }
}
