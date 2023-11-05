package ru.hello_kitties.TG_bot.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Game {
    private int numberOfPlayers;
    private int alreadyParsed = 0;
    private int numberOfSpies;
    List <String> listOfNames = new ArrayList<>();
    List <String> listOfSpy = new ArrayList<>();
    List <String> roles = new ArrayList<>();
    private String location;

    // Геттеры и сеттеры - худшее и безполезнейшее, что есть в программировании.
    public void setLocation(String loc) {
        this.location = loc;
    }
    public String getLocation() {
        return location;
    }
    public void setNumberOfPlayers(int num) {
        this.numberOfPlayers = num;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public List <String> getListOfNames(){
        return this.listOfNames;
    }

    public List<String> getRoles(){
        return this.roles;
    }

    public void setNumberOfSpies(int num) {
        this.numberOfSpies = num;
    }

    public int getAlreadyParsed(){
        return this.alreadyParsed;
    }

    private String getFormOfName(int amount){
        if (amount == 1) return "имя";
        if (amount > 1 && amount < 5) return "имени";
        return "имён";
    }
    public String parseNames(String string) {
        List<String> names = List.of(string.split(", "));
        String answer;
        if (names.size() > numberOfPlayers - alreadyParsed) {
            listOfNames.addAll(names.subList(0, numberOfPlayers - alreadyParsed));
            answer = String.format("Вы ввели %d %s, но нужно было %d. Первые %d %s записаны, остальные не учтены",
                    names.size(), getFormOfName(names.size()), numberOfPlayers-alreadyParsed, numberOfPlayers-alreadyParsed, getFormOfName(numberOfPlayers-alreadyParsed));
            alreadyParsed = numberOfPlayers;
        }
        else {
            listOfNames.addAll(names);
            alreadyParsed += names.size();
            answer = String.format("Вы ввели %d %s, осталось %d", names.size(), getFormOfName(names.size()), numberOfPlayers - alreadyParsed);
        }
        return answer;
    }

    public void generateRoles() {
        Random random = new Random();
        List<Integer> spies = new ArrayList<>();
        for(int i = 0; i < numberOfSpies; i++) {
            int spy = random.nextInt(numberOfPlayers);
            // Во время генерации случайных чисел могут возникнуть повторения, цикл чтобы избежать этого
            while (spies.contains(spy)) { spy = random.nextInt(numberOfPlayers); }
            spies.add(spy);
        }
        for(int i = 0; i < numberOfPlayers; i++) {
            roles.add(spies.contains(i) ? "шпион":"мирный");
        }
    }
}
