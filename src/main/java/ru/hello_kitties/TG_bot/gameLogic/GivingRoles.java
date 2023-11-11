package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.GameState;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.Game;



public class GivingRoles implements GameState {
    @Override
    public void makeGame(Game game, BotRequest request, Update update) {
        // sent определяет, нужно ли нам отправить сообщение с ролью, или удалить сообщение бота (с ролью прошлого игрока)
        if (!game.getSent()) {
            // Удаляем предыдущее сообщение пользователя, т. к. оно бессмысленное (попросили любое)
            game.changeNeedDelete("once");
            // Будущий вывод пользователю в соответствии с ролью
            String text;
            // Получаем и обрабатываем роль текущего игрока (под индексом current)
            if (game.getRoles().get(game.getCurrent()).equals("мирный")) {
                text = String.format("%s, ты - мирный житель. Локация - %s\nНапиши любое сообщение", game.getListOfNames().get(game.getCurrent()), game.getLocation());
            } else {
                text = String.format("%s, ты - шпион!\nНапиши любое сообщение ", game.getListOfNames().get(game.getCurrent()));
            }
            // Если это был не последний игрок, просим передать телефон следующему
            if (game.getCurrent()!= game.getNumberOfPlayers() - 1) {
                text += String.format(" и передай игроку %s", game.getListOfNames().get(game.getCurrent() + 1));
            }
            // Отправляем сообщение, переходим к следующему игроку (current++), ставим флаг, что в следующий раз нужно будет удалить сообщение
            request.setRequest(text);
            request.setChatId(update.getMessage().getChatId().toString());
            game.changeCurrent(game.getCurrent()+1);
            game.changeSent(true);
        }
        else {
            // Удаляем сообщение пользователя и бота, т. к. одно бессмысленное, другое - с ролью предыдущего игрока
            game.changeNeedDelete("twice");
            // Проверяем, была ли это последняя роль (последний игрок) и переходим дальше (меняем state) если это так
            if (game.getCurrent() == game.getNumberOfPlayers()) {
                request.setRequest("Роли розданы, как будете готовы, наберите любое сообщение");
                request.setChatId(update.getMessage().getChatId().toString());
                game.changeState("running");
            }
            // Обозначаем, что следующее наше сообщение - вывод роли, а не удаление сообщения
            game.changeSent(false);
        }
    }

}
