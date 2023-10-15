package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.logic.BotResponse;

public class Bot extends TelegramLongPollingBot implements AnswerWriter {
    private final String name;
    public Bot(String token, String name){
        super(token);
        this.name = name;
    }
    @Override
    public void writeAnswer(BotResponse response) {

    }
}
