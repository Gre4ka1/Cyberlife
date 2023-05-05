package com.example.cyberlife;

import java.util.ArrayList;

public class BotsRepository {
    private static BotsRepository instance;
    private ArrayList<Bot> bots=new ArrayList<>();

    public void addBot(Bot b){
        System.out.println(bots);
        bots.add(b);

    }

    public BotsRepository() {
    }

    public ArrayList<Bot> getBots() {
        return bots;
    }

    public void setBots(ArrayList<Bot> bots) {
        this.bots = bots;
    }

    public static BotsRepository getInstance() {
        if (instance == null) {
            instance = new BotsRepository();
        }
        return instance;
    }
}
