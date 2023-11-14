package ru.hello_kitties.TG_bot.gameLogic;

import ru.hello_kitties.TG_bot.botLogic.Game;

public class ChoosingPlace implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // Заранее генерируем роли, т. к. следующий if будет выполняться несколько раз
        game.generateRoles();
        // TODO: Здесь нужно в соответствии с прошлым if'ом выбрать локацию
        game.setLocation("какое-то осмысленное место");
        // Предупреждаем пользователя, что сейчас будут выводится роли
        game.setState("giving roles");
        return (String.format("Сейчас будут выводится роли игроков в том порядке, в каком вы вводили имена. " + "Передайте телефон первому игроку (%s) и введите любое сообщение, чтобы начать", game.getListOfNames().get(0)));
    }
}
