package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class EnteringNames implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        // Если ещё остались невведённые имена
        if (game.getAlreadyParsed() < game.getNumberOfPlayers()) {
            // Ключевое тут - вызов game.parseNames (см. комментарии там)
            request.setRequest(game.parseNames(update.getMessage().getText()));
            request.setChatId(update.getMessage().getChatId().toString());
            // Чтобы при вводе сразу всех имён одним сообщением не было ответа "осталось ещё ...", а сразу переход дальше
            /*if (game.getAlreadyParsed() != game.getNumberOfPlayers()) {
                handle.handle(request, this);
            }*/
        }
        // Переход к вводу количества шпионов, если имён введено достаточно
        if (game.getAlreadyParsed() >= game.getNumberOfPlayers()) {
            request.setRequest("Введите количество шпионов:");
            request.setChatId(update.getMessage().getChatId().toString());
            game.changeState("waiting amount spies");
        }
    }
}
