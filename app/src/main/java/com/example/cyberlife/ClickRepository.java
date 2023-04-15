package com.example.cyberlife;

public class ClickRepository {
    private static ClickRepository instance;
    private Integer clickX,clickY;
    private boolean restart;

    public ClickRepository() {
    }

    public Integer getClickX() {
        return clickX;
    }

    public void setClickX(Integer clickX) {
        this.clickX = clickX;
    }

    public Integer getClickY() {
        return clickY;
    }

    public void setClickY(Integer clickY) {
        this.clickY = clickY;
    }

    public boolean getRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public static ClickRepository getInstance() {
        if (instance == null) {
            instance = new ClickRepository();
        }
        return instance;
    }
}
