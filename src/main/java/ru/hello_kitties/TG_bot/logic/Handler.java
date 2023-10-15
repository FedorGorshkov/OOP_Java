package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.MessageHandler;

public class Handler {
    public void handle(BotRequest request, AnswerWriter writer) {
        BotResponse res1 = new BotResponse(request.getRequest());
        writer.writeAnswer(res1);
    };
}
