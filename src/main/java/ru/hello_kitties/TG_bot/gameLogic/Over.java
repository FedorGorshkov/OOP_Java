package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

import java.util.ArrayList;
import java.util.List;

public class Over implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        game.setState("/start");
        game.setCurrent(0);
        game.setUserLastId(-1);
        game.setOurLastId(-1);
        game.setStartTimer(false);
        game.setSent(false);
        game.setNumberOfPlayers(0);
        game.setAlreadyParsed(0);
        game.setNumberOfSpies(0);
        game.setNullListOfNames();
        game.setNullListOfSpy("");
        game.setNullListOfRoles();
        game.setLocation(null);
        game.setTime("null");
        return "<i>Спасибо за игру! Если хотите сыграть ещё раз, введите /start</i>";
    }
}
