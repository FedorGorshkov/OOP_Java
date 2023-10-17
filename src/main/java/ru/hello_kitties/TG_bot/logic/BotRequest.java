package ru.hello_kitties.TG_bot.logic;

public class BotRequest {
    private final String message;

    private final String chatId;

    public BotRequest(String message, String chatId) {
        this.message = message;
        this.chatId = chatId;
    }

    public String getId() {
        return chatId;
    }


    public String getRequest() {
        return message;
    }
}
