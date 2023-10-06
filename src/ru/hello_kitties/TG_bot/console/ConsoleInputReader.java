package ru.hello_kitties.TG_bot.console;

import ru.hello_kitties.TG_bot.InputReader;
import ru.hello_kitties.TG_bot.logic.BotRequest;
import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private static final Scanner SCANNER = new Scanner(System.in);
    public BotRequest getUserInput() {
        System.out.println("Enter request:");
        return new BotRequest(SCANNER.nextLine());
    }
}
