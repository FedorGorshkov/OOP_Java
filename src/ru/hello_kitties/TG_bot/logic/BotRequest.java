package ru.hello_kitties.TG_bot.logic;

import ru.hello_kitties.TG_bot.InputReader;

public class BotRequest /*implements InputReader*/ {
    private String req;

    public String getRequest(){
        return req;
    }
    public void changeRequest(String newMessage){
        this.req = newMessage;
    }
}
