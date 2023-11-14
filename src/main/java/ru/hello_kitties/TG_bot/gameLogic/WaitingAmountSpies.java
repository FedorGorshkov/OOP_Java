package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class WaitingAmountSpies implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // Проверка корректности введённого числа шпионов (не больше половины от игроков)
        if (Integer.parseInt(message) <= (game.getNumberOfPlayers()/2)){
            game.setNumberOfSpies(Integer.parseInt(message));
            // TODO: сюда нужно написать сообщение, описывающее метод выбора пакетов с локациями
            game.setState("choosing place");
            return "Здесь будет выбор пакета локаций, но пока тут ничего нет";
        }
        else
            return "Шпионов должно быть не больше половины игроков!";
    }
}
