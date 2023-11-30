package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.BotResponse;
import ru.hello_kitties.TG_bot.botLogic.Game;
import ru.hello_kitties.TG_bot.botLogic.Handler;
import ru.hello_kitties.TG_bot.gameLogic.*;

import java.util.HashMap;


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

    HashMap<String, Game> games = new HashMap<>();
    Handler handler = new Handler();

    @Override
    public void onUpdateReceived(Update update) {
        Game game;
        String chatId = update.getMessage().getChatId().toString();
        if (games.containsKey(chatId)) {
            game = games.get(chatId);
        }
        else {
            game = new Game();
            games.put(chatId, game);
        }
        game.setUserLastId(update.getMessage().getMessageId());
        handler.handle(this, game, update.getMessage().getText(), chatId);
    }

    @Override
    public int writeAnswer(BotResponse response) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(response.getChatId());
            sendMessage.setText(response.getResponse());
            sendMessage.setParseMode(ParseMode.HTML);
            Message msg = execute(sendMessage);
            return msg.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteMessage(String chatId, int lastId) {
        try {
            if(lastId != -1)
                execute(new DeleteMessage(chatId, lastId));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void editMessage(int ourId, String message, String chatId){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(ourId);
        editMessageText.setText(message);
        editMessageText.setParseMode(ParseMode.HTML);
        try {
            execute(editMessageText);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
