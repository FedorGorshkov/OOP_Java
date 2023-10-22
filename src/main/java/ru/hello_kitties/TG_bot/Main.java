package ru.hello_kitties.TG_bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hello_kitties.TG_bot.telegram.*;
import ru.hello_kitties.TG_bot.logic.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String args[])
    {
        try {
            Properties properties1 = new Properties();
            properties1.load(new FileInputStream("bot.properties"));

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot("hello_kitties_bot", "6455669284:AAHy_S38_L2eLksPvdUnjkpZQrlPS1hzcJ8"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
