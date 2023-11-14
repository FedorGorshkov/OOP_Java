package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class EnteringNames implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // Если ещё остались невведённые имена
        if (game.getAlreadyParsed() < game.getNumberOfPlayers()) {
            // Ключевое тут - вызов game.parseNames (см. комментарии там)
            String answer = game.parseNames(message);
            if (game.getAlreadyParsed() < game.getNumberOfPlayers())
                return answer;
        }
        // Переход к вводу количества шпионов, если имён введено достаточно
        if (game.getAlreadyParsed() >= game.getNumberOfPlayers()) {
            game.setState("waiting amount spies");
            return "Введите количество шпионов:";
        }
        return "";
    }
}
