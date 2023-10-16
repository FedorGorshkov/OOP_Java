package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.MessageHandler;
import ru.hello_kitties.TG_bot.telegram.InputReaderImplements;

public class Handler {
    public void handle(BotRequest request, AnswerWriter writer) {
        BotResponse response = new BotResponse(request.getRequest(), request.getId());
        writer.writeAnswer(response);
    };
}
