package ru.hello_kitties.TG_bot.telegram;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    Game game = new Game();
    Handler handle = new Handler();


    @Override
    public void onUpdateReceived(Update update) {
        try{
            if (update.hasMessage() && update.getMessage().hasText()){
                BotRequest request = new BotRequest();
                if (game.getState().equals("/start")){
                    Start start = new Start();
                    start.makeGame(game,request,update);
                }
                // Ввод количества игроков
                else if (game.getState().equals("waiting amount players")){
                    WaitingAmountPlayers waitingAmountPlayers = new WaitingAmountPlayers();
                    waitingAmountPlayers.makeGame(game,request,update);
                }
                // Ввод имён игроков
                else if (game.getState().equals("entering names")){
                    EnteringNames enteringNames = new EnteringNames();
                    enteringNames.makeGame(game,request,update);
                }
                // Ввод количества шпионов
                else if(game.getState().equals("waiting amount spies")){
                    WaitingAmountSpies waitingAmountSpies = new WaitingAmountSpies();
                    waitingAmountSpies.makeGame(game,request,update);
                }
                // Выбор пакета с локациями
                else if(game.getState().equals("choosing place")){
                    ChoosingPlace choosingPlace = new ChoosingPlace();
                    choosingPlace.makeGame(game,request,update);
                }
                // Вывод ролей. Этот if выполняется несколько раз
                else if(game.getState().equals("giving roles")){
                    GivingRoles givingRoles = new GivingRoles();
                    givingRoles.makeGame(game,request,update);
                    MessageDelete(update);
                }
                // TODO: сделать таймер игры и досрочное завершение, если все шпионы раскрыты
                else if(game.getState().equals("running")){
                    Running running = new Running();
                    running.makeGame(game,request,update);
                }
                // Тут уже всё готово, прощальное сообщение
                else if(game.getState().equals("over")){
                    Over over = new Over();
                    over.makeGame(game,request,update);
                }
                handle.handle(request, this);
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void writeAnswer(BotResponse response) {
        try {
            Message msg = execute(new SendMessage(response.getChatId(), response.getResponse()));
            // Записываем id последнего отправленного нами сообщения (нужно для удаления в выдаче ролей)
            game.changeLastId(msg.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void MessageDelete(Update update){
        try {
            if(game.getNeedDelete().equals("once")){
                execute(new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId()));
                game.changeNeedDelete("false");
            }
            else if(game.getNeedDelete().equals("twice")){
                execute((new DeleteMessage(update.getMessage().getChatId().toString(), game.getLastId())));
                execute((new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId())));
                game.changeNeedDelete("false");
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
