package com.example.cyberlife;

public class CodeRepositiry {
    private static CodeRepositiry instance;
    private short[] code;

    private CodeRepositiry() {
    }

    public short[] getCode() {
        return code;
    }

    public void updateCode() {
        // TODO: release me
    }

    public static CodeRepositiry getInstance() {
        if (instance == null) {
            instance = new CodeRepositiry();
        }

        return instance;
    }


}
