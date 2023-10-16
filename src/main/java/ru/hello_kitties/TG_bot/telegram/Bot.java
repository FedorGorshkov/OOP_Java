package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.InputReader;
import ru.hello_kitties.TG_bot.logic.BotRequest;
import ru.hello_kitties.TG_bot.logic.BotResponse;
import ru.hello_kitties.TG_bot.logic.Handler;

public class Bot extends TelegramLongPollingBot {

    final private String BOT_TOKEN = "6455669284:AAHy_S38_L2eLksPvdUnjkpZQrlPS1hzcJ8";
    final private String BOT_NAME = "hello_kitties_bot";

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                BotRequest request = new BotRequest();
                InputReader reader = new InputReaderImplements();
                AnswerWriter answer = new AnswerWriterImplements();
                Handler handle= new Handler();

                reader.getUserInput(request);
                handle.handle(request,answer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
