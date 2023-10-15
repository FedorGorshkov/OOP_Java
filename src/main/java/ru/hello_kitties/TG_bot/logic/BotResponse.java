package ru.hello_kitties.TG_bot.logic;

public class BotResponse {
    private final String response;

    public BotResponse(String message) {
        this.response = message;
    }

    public String getResponse() {
        return response;
    }
}
