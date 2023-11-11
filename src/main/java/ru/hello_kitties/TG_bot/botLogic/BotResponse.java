package ru.hello_kitties.TG_bot.botLogic;

public class BotResponse {
    final private String response;
    final private String chatId;

    public BotResponse(String message, String chatId) {
        this.response = message;
        this.chatId = chatId;
    }

    public String getResponse(){
        return response;
    }
    public String getChatId(){
        return chatId;
    }
}
