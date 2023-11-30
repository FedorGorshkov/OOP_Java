package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class WaitingAmountSpies implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // Проверка корректности введённого числа шпионов (не больше половины от игроков)
        if (Integer.parseInt(message) <= (game.getNumberOfPlayers()/2)){
            game.setNumberOfSpies(Integer.parseInt(message));
            game.setState("choosing place");
            return "Сейчас нужно выбрать пакет с локациями:) Выберите из предложенных: базовый/исскуство/путешествие/дети/досуг/спорт/хардкор/всё";
        }
        else
            return "Шпионов должно быть не больше половины игроков!";
    }
}
