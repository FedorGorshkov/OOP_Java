package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.logic.BotRequest;
import ru.hello_kitties.TG_bot.logic.BotResponse;
import ru.hello_kitties.TG_bot.logic.Handler;

public class Bot extends TelegramLongPollingBot implements AnswerWriter {

    final private String BOT_NAME;
    public Bot(String botName, String botToken){
        super(botToken);
        this.BOT_NAME = botName;
    }
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText()) {
                BotRequest request = new BotRequest(update.getMessage().getText(), "");
                Handler handle = new Handler();
                handle.handle(request, this);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeAnswer(BotResponse response) {
        try {
            execute(new SendMessage(response.getChatId(), response.getResponse()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
