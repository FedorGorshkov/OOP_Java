package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.MessageHandler;

public class Handler implements MessageHandler {
    public void handle(BotRequest request, AnswerWriter writer) {
        BotResponse response;
        if(request.getRequest().equals("/start")) {
            response = new BotResponse("Введите количество игроков (от 3 до 100):", request.getId());
        }
        else {
            response = new BotResponse(request.getRequest(), request.getId());
        }
        writer.writeAnswer(response);
    };
}
