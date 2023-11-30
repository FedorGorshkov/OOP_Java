package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;



public class SendRole implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // Будущий вывод пользователю в соответствии с ролью
        String text;
        //String location = game.getLocation();
        // Получаем и обрабатываем роль текущего игрока (под индексом current)
        if (game.getRoles().get(game.getCurrent()).equals("мирный")) {
            text = String.format("%s, ты - <b>мирный житель</b>, локация - <b>%s</b>\nНапиши любое сообщение", game.getListOfNames().get(game.getCurrent()), game.getLocation());
        } else {
            game.setListOfSpy(game.getListOfNames().get(game.getCurrent()));
            text = String.format("%s, ты - <b>шпион!</b>\nНапиши любое сообщение ", game.getListOfNames().get(game.getCurrent()));
        }
        // Если это был не последний игрок, просим передать телефон следующему
        if (game.getCurrent()!= game.getNumberOfPlayers() - 1) {
            text += String.format(" и передай игроку %s", game.getListOfNames().get(game.getCurrent() + 1));
        }
        // Отправляем сообщение, переходим к следующему игроку (current++), ставим флаг, что в следующий раз нужно будет удалить сообщение
        game.setCurrent(game.getCurrent() + 1);
        game.setSent(true);
        return text;
    }
}
