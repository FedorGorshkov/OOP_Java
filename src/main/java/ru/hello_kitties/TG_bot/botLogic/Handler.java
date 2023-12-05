package ru.hello_kitties.TG_bot.botLogic;

import ru.hello_kitties.TG_bot.MessageHandler;
import ru.hello_kitties.TG_bot.gameLogic.*;
import ru.hello_kitties.TG_bot.telegram.Bot;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Handler implements MessageHandler {
    private final Timer timer = new Timer();

    public void handle(Bot bot, Game game, String message, String chatId) {
        BotResponse response = null;
        try {
            if (game.getState().equals("/start"))
                response = new BotResponse(new Start().processMsg(game, message), chatId);
            // Ввод количества игроков
            else if (game.getState().equals("waiting amount players"))
                response = new BotResponse(new WaitingAmountPlayers().processMsg(game, message), chatId);
            // Ввод имён игроков
            else if (game.getState().equals("entering names"))
                response = new BotResponse(new EnteringNames().processMsg(game, message), chatId);
            // Ввод количества шпионов
            else if(game.getState().equals("waiting amount spies")){
                response = new BotResponse(new WaitingAmountSpies().processMsg(game, message), chatId);
            }
            // Выбор пакета с локациями
            else if(game.getState().equals("choosing place"))
                response = new BotResponse(new ChoosingPlace().processMsg(game, message), chatId);
            // Вывод ролей
            else if(game.getState().equals("giving roles")){
                if (!game.getSent()) {
                    bot.deleteMessage(chatId, game.getUserLastId());
                    response = new BotResponse(new SendRole().processMsg(game, message), chatId);
                    game.setSent(true);
                }
                else {
                    bot.deleteMessage(chatId, game.getOurLastId());
                    bot.deleteMessage(chatId, game.getUserLastId());
                    response = new BotResponse(new DeleteRole().processMsg(game, message), chatId);
                }
            }
            // Во время самой игры
            else if(game.getState().equals("running")) {
                //проверка на корректность времени
                if(game.getTime().equals("null")){
                    response = new BotResponse(new Running().processMsg(game, message), chatId);
                }else{
                    //запуск таймера, пока без досрочного выхода
                    int count = Integer.parseInt(game.getTime());
                    game.setStartTimer(true);
                    bot.deleteMessage(chatId, game.getUserLastId());
                    timer.schedule(new TimerTask() {
                        private int innerCounter = count;
                        @Override
                        public void run() {
                            if (innerCounter > 0) {
                                bot.editMessage(game.getOurLastId(), new Running().processMsg(game, message), chatId);
                                innerCounter--;
                            }
                            else {
                                cancel();
                            }
                        }
                        //здесь секунды, для тестов
                    }, 0, 1000);
                }
            }
            // Тут уже всё готово, прощальное сообщение
            else if(game.getState().equals("over"))
                response = new BotResponse(new Over().processMsg(game, message), chatId);
            assert response != null;
            if (!response.getResponse().isEmpty()) {
                // Записываем id последнего отправленного нами сообщения (нужно для удаления в выдаче ролей)
                game.setOurLastId(bot.writeAnswer(response));
            }
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
