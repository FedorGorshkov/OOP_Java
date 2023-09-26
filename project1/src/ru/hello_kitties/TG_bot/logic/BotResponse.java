package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.AnswerWriter;

public class BotResponse {
    private String resp;
    public BotResponse(String message) {
        this.resp = message;
    }

    public String getResponse(){
        return resp;
    }
    public void changeResponse(String newMessage){
        this.resp = newMessage;
    }
}
