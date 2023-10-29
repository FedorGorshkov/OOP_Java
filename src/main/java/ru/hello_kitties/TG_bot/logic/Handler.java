package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.MessageHandler;

import java.util.Objects;

public class Handler implements MessageHandler {
    public void handle(BotRequest request, AnswerWriter writer) {
        if (Objects.equals(request.getRequest(), "начать!")) {
            BotResponse response = new BotResponse("введите колличество игроков (от 3 до 100)", request.getId());
            writer.writeAnswer(response);
        }else{
            BotResponse response = new BotResponse(request.getRequest(), request.getId());
            writer.writeAnswer(response);
        }
    };
}
