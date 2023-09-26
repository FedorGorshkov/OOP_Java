package ru.hello_kitties.TG_bot.console;

import ru.hello_kitties.TG_bot.InputReader;
import ru.hello_kitties.TG_bot.logic.BotRequest;
import java.util.Scanner;

public class ImplemInputReader implements InputReader {
    public BotRequest getUserInput(BotRequest request){
        Scanner scan = new Scanner(System.in);
        System.out.println("enter request:");
        request.changeRequest(scan.nextLine());
        return null;
    }
}
