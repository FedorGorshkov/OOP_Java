package ru.hello_kitties.TG_bot.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int numberOfPlayers;
    private int numberOfSpy;
    List <String> listOfNames = new ArrayList<>();
    List <String> listOfSpy = new ArrayList<>();


    public void setNumberOfPlayers(int num) {
        this.numberOfPlayers = num;
    }

    public void setNumberOfSpy(int num) {
        this.numberOfSpy = num;
    }

    public int getNumberOf() {
        return this.numberOfPlayers;
    }

    public void getNames(String string) {
        listOfNames.add(string);
    }

    /*Random randomizer = new Random();
    String random = listOfNames.get(randomizer.nextInt(listOfNames.size()));*/
}
