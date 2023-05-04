package com.example.cyberlife;

import java.util.ArrayList;

public class BotsRepository {
    private static BotsRepository instance;
    private ArrayList<Bot> bots;

    public void addBot(Bot b){
        bots.add(b);

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
