package ru.hello_kitties.TG_bot.console;

import ru.hello_kitties.TG_bot.logic.*;
import ru.hello_kitties.TG_bot.*;

public class Main {
    public static  void main(String[] args){
        while(true){
            BotRequest req1 = new BotRequest();
            InputReader reader = new ImplemInputReader();
            reader.getUserInput(req1);
            new BotResponse(req1.getRequest());
            AnswerWriter ans1 = new ImplemAnswerWriter();
            Handler hand1= new Handler();
            hand1.handle(req1,ans1);
        }
    }
}
