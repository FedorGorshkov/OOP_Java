package ru.hello_kitties.TG_bot.telegram;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hello_kitties.TG_bot.InputReader;
import ru.hello_kitties.TG_bot.logic.BotRequest;

public class InputReaderImplements implements InputReader{
    public Update update;

    @Override
    public BotRequest getUserInput(BotRequest request) {
        //Извлекаем из объекта сообщение пользователя
        Message inMess = update.getMessage();
        //Достаем из inMess id чата пользователя
        request.setId(inMess.getChatId().toString());
        //отправляем сообщение в класс запроса
        request.setRequest(inMess.getText());
        return null;
    }
}
