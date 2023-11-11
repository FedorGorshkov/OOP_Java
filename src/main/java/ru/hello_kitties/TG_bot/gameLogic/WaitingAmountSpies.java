package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class WaitingAmountSpies implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        // Проверка корректности введённого числа шпионов (не больше половины от игроков)
        if (Integer.parseInt(update.getMessage().getText()) <= (game.getNumberOfPlayers()/2)){
            game.setNumberOfSpies(Integer.parseInt(update.getMessage().getText()));
            // TODO: сюда нужно написать сообщение, описывающее метод выбора пакетов с локациями
            request.setRequest("Здесь будет выбор пакета локаций, но пока тут ничего нет");
            request.setChatId(update.getMessage().getChatId().toString());
            game.changeState("choosing place");
        }
        else {
            request.setRequest("Шпионов должно быть не больше половины игроков!");
            request.setChatId(update.getMessage().getChatId().toString());
        }
    }
}
