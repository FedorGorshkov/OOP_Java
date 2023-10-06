package ru.hello_kitties.TG_bot.logic;

public class BotRequest {
    private final String message;

    public BotRequest(String req) {
        this.message = req;
    }

    public String getRequest() {
        return message;
    }
}
