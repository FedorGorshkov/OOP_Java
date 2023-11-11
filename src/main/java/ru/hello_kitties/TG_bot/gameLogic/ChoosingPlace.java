package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class ChoosingPlace implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        // Заранее генерируем роли, т. к. следующий if будет выполняться несколько раз
        game.generateRoles();
        // TODO: Здесь нужно в соответствии с прошлым if'ом выбрать локацию
        game.setLocation("какое-то осмысленное место");
        // Предупреждаем пользователя, что сейчас будут выводится роли
        request.setRequest(String.format("Сейчас будут выводится роли игроков в том порядке, в каком вы вводили имена. " +
                "Передайте телефон первому игроку (%s) и введите любое сообщение, чтобы начать", game.getListOfNames().get(0)));
        request.setChatId(update.getMessage().getChatId().toString());
        game.changeState("giving roles");
    }
}
