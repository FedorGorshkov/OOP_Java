package ru.hello_kitties.TG_bot.telegram;
import java.util.List;
import java.util.ArrayList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
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

    public String state = "/start";
    // Индекс роли, которую мы вывели последней
    private int current = 0;
    private int last_id = -1;
    private boolean sent = false;
    int countNames = 0;
    Game game = new Game();
    Handler handle = new Handler();
    @Override
    public void onUpdateReceived(Update update) {
        try{
            if (update.hasMessage() && update.getMessage().hasText() && state.equals("/start")){
                BotRequest request = new BotRequest(update.getMessage().getText(),update.getMessage().getChatId().toString());
                handle.handle(request,this);
                state = "waiting amount players";
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("waiting amount players")) {
                game.setNumberOfPlayers(Integer.parseInt(update.getMessage().getText()));
                BotRequest request = new BotRequest("Вводите имена игроков (можно по одному, либо по несколько сразу через запятую):",
                        update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "entering names";
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("entering names")){
                if (game.getAlreadyParsed() < game.getNumberOfPlayers()) {
                    BotRequest request = new BotRequest(game.parseNames(update.getMessage().getText()),
                            update.getMessage().getChatId().toString());
                    if (game.getAlreadyParsed() != game.getNumberOfPlayers()) {
                        handle.handle(request, this);
                    }
                }
                if (game.getAlreadyParsed() >= game.getNumberOfPlayers()) {
                    BotRequest request = new BotRequest("Введите количество шпионов:", update.getMessage().getChatId().toString());
                    state = "waiting amount spies";
                    handle.handle(request, this);
                }
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("waiting amount spies")) {
                if (Integer.parseInt(update.getMessage().getText()) <= (game.getNumberOfPlayers()/2)){
                    game.setNumberOfSpies(Integer.parseInt(update.getMessage().getText()));
                    BotRequest request = new BotRequest("Здесь будет выбор пакета локаций, но пока тут ничего нет", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                    state = "choosing place";
                }
                else {
                    BotRequest request = new BotRequest("Шпионов должно быть не больше половины игроков!", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                }
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("choosing place")) {
                // Заранее генерируем роли, т. к. следующий if будет выполняться несколько раз
                game.generateRoles();
                game.setLocation("какое-то осмысленное место");
                BotRequest request = new BotRequest(String.format("Сейчас будут выводится роли игроков в том порядке, в каком вы вводили имена. " +
                        "Передайте телефон первому игроку (%s) и введите любое сообщение, чтобы начать", game.getListOfNames().get(0)),  update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "giving roles";
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("giving roles")) {
                 if (!sent) {
                    execute(new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId()));
                    String text;
                    if (game.getRoles().get(current).equals("мирный")) {
                        text = String.format("%s, ты - мирный житель. Локация - %s\nНапиши любое сообщение", game.getListOfNames().get(current), game.getLocation());
                    } else {
                        text = String.format("%s, ты - шпион!\nНапиши любое сообщение ", game.getListOfNames().get(current));
                    }
                    if (current != game.getNumberOfPlayers() - 1) {
                        text += String.format(" и передай игроку %s", game.getListOfNames().get(current + 1));
                    }
                    BotRequest request = new BotRequest(text, update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                    current++;
                    sent = true;
                }
                else {
                    execute(new DeleteMessage(update.getMessage().getChatId().toString(), last_id));
                    execute(new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId()));
                     if (current == game.getNumberOfPlayers()) {
                         BotRequest request = new BotRequest("Роли розданы, как будете готовы, наберите любое сообщение", update.getMessage().getChatId().toString());
                         handle.handle(request, this);
                         state = "running";
                     }
                    sent = false;
                }
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("running")) {
                BotRequest request = new BotRequest("Здесь будет какой-то текст, связанный с временем игры", update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "over";
            }
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("over")) {
                BotRequest request = new BotRequest("Спасибо за игру! Если хотите сыграть ещё раз, введите /start", update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "/start";
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void writeAnswer(BotResponse response) {
        try {
            Message msg = execute(new SendMessage(response.getChatId(), response.getResponse()));
            last_id = msg.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
