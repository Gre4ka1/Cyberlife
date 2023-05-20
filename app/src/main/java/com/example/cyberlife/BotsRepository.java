package com.example.cyberlife;

import java.util.ArrayList;

public class BotsRepository {
    private static BotsRepository instance;
    private ArrayList<Bot> bots;
    private Bot infoBot;

    public void addBot(Bot b){
        System.out.println(bots);
        bots.add(b);

    }

    public BotsRepository() {
    }

    public ArrayList<Bot> getBots() {
        return bots;
    }


    public Bot getInfoBot() {
        return infoBot;
    }

    public void setInfoBot(Bot infoBot) {
        this.infoBot = infoBot;
    }

    public void setBots(ArrayList<Bot> bots) {
        this.bots = bots;
    }
    public void delBot(Bot b){
        bots.remove(b);
    }
    public static BotsRepository getInstance() {
        if (instance == null) {
            instance = new BotsRepository();
        }
        return instance;
    }
    public void init(){
        bots=new ArrayList<>();
    }
    public void clear(){
        bots.clear();
    }
}
