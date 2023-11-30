package ru.hello_kitties.TG_bot.botLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.util.Scanner;


public class Game {
    private String state = "/start";
    public void setState (String enterState){
        this.state = enterState;
    }
    public String getState(){
        return state;
    }
    private int current = 0;
    public void setCurrent(int enterCurrent){
        this.current = enterCurrent;
    }
    public int getCurrent(){
        return current;
    }
    private int userLastId = -1;
    public void setUserLastId(int id) {
        this.userLastId = id;
    }
    public int getUserLastId(){
        return userLastId;
    }
    private int ourLastId = -1;
    public void setOurLastId(int id){
        this.ourLastId = id;
    }
    public int getOurLastId(){
        return ourLastId;
    }
    private boolean startTimer = false;
    public void setStartTimer(boolean enterStart){
        this.startTimer = enterStart;
    }
    public boolean getStartTimer(){
        return startTimer;
    }
    private boolean sent = false;
    public void setSent(boolean enterSent){
        this.sent = enterSent;
    }
    public boolean getSent(){
        return sent;
    }
    private int numberOfPlayers;
    public void setNumberOfPlayers(int num) {
        this.numberOfPlayers = num;
    }
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }
    private int alreadyParsed = 0;
    public int getAlreadyParsed(){
        return this.alreadyParsed;
    }
    private int numberOfSpies;
    public void setNumberOfSpies(int num) {
        this.numberOfSpies = num;
    }
    List <String> listOfNames = new ArrayList<>();
    public List <String> getListOfNames(){
        return this.listOfNames;
    }
    String listOfSpy = "";
    public String getListOfSpy(){
        return this.listOfSpy;
    }
    public void setListOfSpy(String spy){this.listOfSpy = this.listOfSpy + spy + " ";}

    List <String> roles = new ArrayList<>();
    public List<String> getRoles(){
        return this.roles;
    }
    private String location;
    public void setLocation(String loc) {
        this.location = loc;
    }
    public String getLocation() {
        return location;
    }
    private String time = "null";
    public void setTime(String tm) {
        this.time= tm;
    }
    public String getTime() {
        return this.time;
    }


    // Забавная функция (не метод) ради красивого вывода, определяет форму слова "имя" с числительным, чтобы не нарушать правила русского языка
    private String getFormOfName(int amount){
        if (amount == 1) return "имя";
        if (amount > 1 && amount < 5) return "имени";
        return "имён";
    }
    public String getFormOfSpy (){
        if (numberOfSpies == 1) {
            return "шпион";
        }else {
            return "шпионы";
        }
    }

    // Метод, принимающий одно или несколько имён, записывающий их в поле класса и возвращающий ответ для пользователя
    // (удобнее было сделать составление ответа здесь)
    public String parseNames(String string) {
        // Разбиваем строку-сообщение на имена по запятой с пробелом
        List<String> names = List.of(string.split(", "));
        // Заготовка под ответ пользователю
        String answer;
        // Если имён введено больше, чем нужно
        if (names.size() > numberOfPlayers - alreadyParsed) {
            // Добавляем только столько первых, чтобы получилось нужное количество имён
            listOfNames.addAll(names.subList(0, numberOfPlayers - alreadyParsed));
            // Ответ для пользователя
            answer = String.format("Вы ввели %d %s, но нужно было %d. Первые %d %s записаны, остальные не учтены",
                    names.size(), getFormOfName(names.size()), numberOfPlayers-alreadyParsed, numberOfPlayers-alreadyParsed,
                    getFormOfName(numberOfPlayers-alreadyParsed));
            // Обозначаем, что ввод окончен
            alreadyParsed = numberOfPlayers;
        }
        // Если введено ровно столько имён, сколько нужно, или меньше
        else {
            // Добавляем все имена
            listOfNames.addAll(names);
            // Обозначаем, что приняли новые имена
            alreadyParsed += names.size();
            // Ответ для пользователя (не выводится, если введены все имена сразу)
            answer = String.format("Вы ввели %d %s, осталось %d", names.size(), getFormOfName(names.size()), numberOfPlayers - alreadyParsed);
        }
        return answer;
    }

    // Метод, генерирующий случайным образом роли и записывающий их в соотв. поле класса
    public void generateRoles() {
        // Генератор случайных чисел
        Random random = new Random();
        // Список, содержащий индексы в будущем списке ролей, соотв. элементы которых будут шпионами
        List<Integer> spies = new ArrayList<>();
        // Генерируем numberOfSpies шпионов
        for(int i = 0; i < numberOfSpies; i++) {
            // Случайное число от 0 до numberOfPlayers - 1
            int spy = random.nextInt(numberOfPlayers);
            // Во время генерации случайных чисел могут возникнуть повторения, цикл чтобы избежать этого
            while (spies.contains(spy)) { spy = random.nextInt(numberOfPlayers); }
            spies.add(spy);
        }
        // Заполняем поле класса, обозначая элементы под индексами из spies как шпионов, остальных как мирных
        for(int i = 0; i < numberOfPlayers; i++) {
            roles.add(spies.contains(i) ? "шпион":"мирный");
        }
    }

    public void choosingLocation(){
        if(location.equals("всё")) {
            List<String> locations = new ArrayList<>(List.of("искусство","базовый","дети","хардкор","путешествие","досуг","спорт"));
            Random random = new Random();
            location = locations.get(random.nextInt(locations.size()));
        }
        List<String> allLocations = new ArrayList<>();
        Random random = new Random();
        if(location.equals("искусство")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/art"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("базовый")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/basic"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("дети")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/children"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("хардкор")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/craziness"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("путешествие")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/journey"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("досуг")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/leisure"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(location.equals("спорт")) {
            try (Scanner scanner = new Scanner(new File("src/main/locations/sport"))) {
                while (scanner.hasNextLine()){
                    allLocations.add(scanner.nextLine());
                }
                location = allLocations.get(random.nextInt(allLocations.size()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            location = "error";
        }
    }
    public void Timer(){
        int intTime = Integer.parseInt(this.time)-1;
        this.time = String.format("%d",intTime);
    }
}
