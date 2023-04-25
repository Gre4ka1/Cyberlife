package com.example.cyberlife;

import java.util.ArrayList;

public class BotsRepository {
    private static BotsRepository instance;
    private ArrayList<Bot> bots;

    public void addBot(Bot b){
        bots.add(b);
    }


    public static BotsRepository getInstance() {
        if (instance == null) {
            instance = new BotsRepository();
        }
        return instance;
    }
}
