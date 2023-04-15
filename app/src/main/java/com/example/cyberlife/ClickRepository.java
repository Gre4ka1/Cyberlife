package com.example.cyberlife;

public class ClickRepository {
    private static Integer clickX,clickY;

    public static Integer getClickX() {
        return clickX;
    }

    public static void setClickX(Integer clickX) {
        ClickRepository.clickX = clickX;
    }

    public static Integer getClickY() {
        return clickY;
    }

    public static void setClickY(Integer clickY) {
        ClickRepository.clickY = clickY;
    }
}
