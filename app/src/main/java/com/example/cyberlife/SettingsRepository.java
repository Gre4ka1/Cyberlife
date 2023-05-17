package com.example.cyberlife;

public class SettingsRepository {
    private static SettingsRepository instance;

    private int numberOfBots;
    private int sunEnergy;
    private int energyConsumption;
    private int mutation;
    private int LindemannsRule;


    public int getLindemannsRule() {return LindemannsRule;}
    public void setLindemannsRule(int lindemannsRule) {LindemannsRule = lindemannsRule;}
    public int getEnergyConsumption() {return energyConsumption;}
    public void setEnergyConsumption(int energyConsumption) {this.energyConsumption = energyConsumption;}
    public int getSunEnergy() {return sunEnergy;}
    public void setSunEnergy(int sunEnergy) {this.sunEnergy = sunEnergy;}
    public int getMutation() {return mutation;}
    public void setMutation(int mutation) {this.mutation = mutation;}
    public int getNumberOfBots() {return numberOfBots;}
    public void setNumberOfBots(int numberOfBots) {this.numberOfBots = numberOfBots;}

    public static SettingsRepository getInstance() {
        if (instance == null) {
            instance = new SettingsRepository();
        }
        return instance;
    }
}
