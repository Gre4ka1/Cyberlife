package com.example.cyberlife;

public class CodeRepository {
    private static CodeRepository instance;
    private short[] code;

    private CodeRepository() {
    }

    public short[] getCode() {
        return code;
    }

    public void updateCode(short[] code) {
        this.code=code;
    }

    public static CodeRepository getInstance() {
        if (instance == null) {
            instance = new CodeRepository();
        }

        return instance;
    }


}
