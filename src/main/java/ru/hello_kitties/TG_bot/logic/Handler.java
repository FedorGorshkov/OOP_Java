package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.MessageHandler;

public class Handler implements MessageHandler{
    public void handle(BotRequest request, AnswerWriter writer) {
        BotResponse response = new BotResponse(request.getRequest(), request.getId());
        writer.writeAnswer(response);
    };
}
