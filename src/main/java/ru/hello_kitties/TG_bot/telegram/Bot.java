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
    // id последнего отправленного ботом сообщения (нужно для удаления во время выдачи ролей)
    private int last_id = -1;
    // Переключатель для if'а раздачи роли, определяет, нам сейчас нужно удалить или отправить сообщение
    private boolean sent = false;
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
            // Ввод количества игроков
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("waiting amount players")) {
                game.setNumberOfPlayers(Integer.parseInt(update.getMessage().getText()));
                BotRequest request = new BotRequest("Вводите имена игроков (можно по одному, либо по несколько сразу через запятую с пробелом):",
                        update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "entering names";
            }
            // Ввод имён игроков
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("entering names")){
                // Если ещё остались невведённые имена
                if (game.getAlreadyParsed() < game.getNumberOfPlayers()) {
                    // Ключевое тут - вызов game.parseNames (см. комментарии там)
                    BotRequest request = new BotRequest(game.parseNames(update.getMessage().getText()),
                            update.getMessage().getChatId().toString());
                    // Чтобы при вводе сразу всех имён одним сообщением не было ответа "осталось ещё ...", а сразу переход дальше
                    if (game.getAlreadyParsed() != game.getNumberOfPlayers()) {
                        handle.handle(request, this);
                    }
                }
                // Переход к вводу количества шпионов, если имён введено достаточно
                if (game.getAlreadyParsed() >= game.getNumberOfPlayers()) {
                    BotRequest request = new BotRequest("Введите количество шпионов:", update.getMessage().getChatId().toString());
                    state = "waiting amount spies";
                    handle.handle(request, this);
                }
            }
            // Ввод количества шпионов
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("waiting amount spies")) {
                // Проверка корректности введённого числа шпионов (не больше половины от игроков)
                if (Integer.parseInt(update.getMessage().getText()) <= (game.getNumberOfPlayers()/2)){
                    game.setNumberOfSpies(Integer.parseInt(update.getMessage().getText()));
                    // TODO: сюда нужно написать сообщение, описывающее метод выбора пакетов с локациями
                    BotRequest request = new BotRequest("Здесь будет выбор пакета локаций, но пока тут ничего нет", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                    state = "choosing place";
                }
                else {
                    BotRequest request = new BotRequest("Шпионов должно быть не больше половины игроков!", update.getMessage().getChatId().toString());
                    handle.handle(request, this);
                }
            }
            // Выбор пакета с локациями
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("choosing place")) {
                // Заранее генерируем роли, т. к. следующий if будет выполняться несколько раз
                game.generateRoles();
                // TODO: Здесь нужно в соответствии с прошлым if'ом выбрать локацию
                game.setLocation("какое-то осмысленное место");
                // Предупреждаем пользователя, что сейчас будут выводится роли
                BotRequest request = new BotRequest(String.format("Сейчас будут выводится роли игроков в том порядке, в каком вы вводили имена. " +
                        "Передайте телефон первому игроку (%s) и введите любое сообщение, чтобы начать", game.getListOfNames().get(0)),  update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "giving roles";
            }
            // Вывод ролей. Этот if выполняется несколько раз
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("giving roles")) {
                // sent определяет, нужно ли нам отправить сообщение с ролью, или удалить сообщение бота (с ролью прошлого игрока)
                 if (!sent) {
                     // Удаляем предыдущее сообщение пользователя, т. к. оно бессмысленное (попросили любое)
                     execute(new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId()));
                     // Будущий вывод пользователю в соответствии с ролью
                     String text;
                     // Получаем и обрабатываем роль текущего игрока (под индексом current)
                     if (game.getRoles().get(current).equals("мирный")) {
                         text = String.format("%s, ты - мирный житель. Локация - %s\nНапиши любое сообщение", game.getListOfNames().get(current), game.getLocation());
                     } else {
                         text = String.format("%s, ты - шпион!\nНапиши любое сообщение ", game.getListOfNames().get(current));
                     }
                     // Если это был не последний игрок, просим передать телефон следующему
                     if (current != game.getNumberOfPlayers() - 1) {
                         text += String.format(" и передай игроку %s", game.getListOfNames().get(current + 1));
                     }
                     // Отправляем сообщение, переходим к следующему игроку (current++), ставим флаг, что в следующий раз нужно будет удалить сообщение
                     BotRequest request = new BotRequest(text, update.getMessage().getChatId().toString());
                     handle.handle(request, this);
                     current++;
                     sent = true;
                }
                else {
                    // Удаляем сообщение пользователя и бота, т. к. одно бессмысленное, другое - с ролью предыдущего игрока
                    execute(new DeleteMessage(update.getMessage().getChatId().toString(), last_id));
                    execute(new DeleteMessage(update.getMessage().getChatId().toString(), update.getMessage().getMessageId()));
                    // Проверяем, была ли это последняя роль (последний игрок) и переходим дальше (меняем state) если это так
                    if (current == game.getNumberOfPlayers()) {
                        BotRequest request = new BotRequest("Роли розданы, как будете готовы, наберите любое сообщение", update.getMessage().getChatId().toString());
                        handle.handle(request, this);
                        state = "running";
                    }
                    // Обозначаем, что следующее наше сообщение - вывод роли, а не удаление сообщения
                    sent = false;
                }
            }
            // TODO: сделать таймер игры и досрочное завершение, если все шпионы раскрыты
            else if (update.hasMessage() && update.getMessage().hasText() && state.equals("running")) {
                BotRequest request = new BotRequest("Здесь будет какой-то текст, связанный с временем игры", update.getMessage().getChatId().toString());
                handle.handle(request, this);
                state = "over";
            }
            // Тут уже всё готово, прощальное сообщение
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
            // Записываем id последнего отправленного нами сообщения (нужно для удаления в выдаче ролей)
            last_id = msg.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
