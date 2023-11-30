package ru.hello_kitties.TG_bot.gameLogic;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import ru.hello_kitties.TG_bot.botLogic.BotRequest;
import ru.hello_kitties.TG_bot.botLogic.BotResponse;
import ru.hello_kitties.TG_bot.botLogic.Game;

public class Running implements GameStage {
    @Override
    public String processMsg(Game game, String message) {
        // TODO: сделать досрочное завершение, если все шпионы раскрыты
        if (!game.getStartTimer()){
            if (Integer.parseInt(message) <61 && Integer.parseInt(message)>0){
                game.setTime(String.format("%d",Integer.parseInt(message)+1));
                return "отлично! чтобы игра началась введите любое сообщение";
            }else{return "выберите время из диапазона";}
        }else{
            if(Integer.parseInt(game.getTime())>1){
                game.Timer();
                return String.format("осталось %s минут",game.getTime());
            }else{
                game.setState("over");
                return String.format("%s-%s! место-%s \n <i>Мирные жители проиграли</i>", game.getListOfSpy(), game.getFormOfSpy(), game.getLocation());
            }
        }
    }
}
