package ru.hello_kitties.TG_bot.botLogic;

public class BotRequest {
    private String message;

    private String chatId;

    public void setRequest(String message){
        this.message = message;
    }
    public void setChatId(String id){
        this.chatId = id;
    }

    public String getId() {
        return chatId;
    }


    public String getRequest() {
        return message;
    }
}
