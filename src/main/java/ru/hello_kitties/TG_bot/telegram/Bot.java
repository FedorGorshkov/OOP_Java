package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hello_kitties.TG_bot.AnswerWriter;
import ru.hello_kitties.TG_bot.logic.BotRequest;
import ru.hello_kitties.TG_bot.logic.BotResponse;
import ru.hello_kitties.TG_bot.logic.Game;
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

    public String statement = "начать";
    int countNames = 1;
    Game game = new Game();
    Handler handle = new Handler();
    @Override
    public void onUpdateReceived(Update update) {
        try{
            if (update.hasMessage() && update.getMessage().hasText() && statement.equals("начать")){
                BotRequest request = new BotRequest(update.getMessage().getText(),update.getMessage().getChatId().toString());
                handle.handle(request,this);
                statement = "кол игроки";

            } else if (update.hasMessage() && update.getMessage().hasText() && statement.equals("кол игроки")) {
                game.setNumberOfPlayers(Integer.parseInt(update.getMessage().getText()));
                BotRequest request = new BotRequest("введите имена игроков:", update.getMessage().getChatId().toString());
                handle.handle(request, this);
                statement = "ввод имён";

            } else if (update.hasMessage() && update.getMessage().hasText() && statement.equals("ввод имён")){
                if (countNames < game.getNumberOf()) {
                    game.getNames(update.getMessage().getText());
                    countNames+=1;
                }else {
                    BotRequest request = new BotRequest("введите количество шпионов:", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                    statement = "кол шпионы";
                }

            } else if (update.hasMessage() && update.getMessage().hasText() && statement.equals("кол шпионы")){
                if (Integer.parseInt(update.getMessage().getText()) <= (game.getNumberOf()/2)){
                    game.setNumberOfSpy(Integer.parseInt(update.getMessage().getText()));
                    BotRequest request = new BotRequest("начать игру?", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                    statement = "о";
                }else{
                    BotRequest request = new BotRequest("шпионов должно быть не больше половины игроков!", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                }
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
