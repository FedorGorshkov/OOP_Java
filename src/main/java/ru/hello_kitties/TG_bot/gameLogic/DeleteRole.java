package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class DeleteRole implements GameStage{
    @Override
    public String processMsg(Game game, String message) {
        game.setSent(false);
        if (game.getCurrent() == game.getNumberOfPlayers()) {
            game.setState("running");
            return "Роли розданы, как будете готовы, наберите любое сообщение";
        }
        return "";
    }
}
