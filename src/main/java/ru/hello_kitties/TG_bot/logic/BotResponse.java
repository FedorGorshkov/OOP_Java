package ru.hello_kitties.TG_bot.logic;

public class BotResponse {
    final private String response;

    public BotResponse(String message, String chatId) {
        this.response = message+chatId;
    }

    public String getResponse(){
        return response;
    }
}
