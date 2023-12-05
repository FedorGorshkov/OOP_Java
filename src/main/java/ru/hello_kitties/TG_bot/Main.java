package ru.hello_kitties.TG_bot;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hello_kitties.TG_bot.telegram.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args)
    {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/bot.properties"));
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot(properties.getProperty("Name"), properties.getProperty("Token")));
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
