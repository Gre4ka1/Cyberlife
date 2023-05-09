package com.example.cyberlife;

public class Code {
    private short[] code = new short[16];

    public short[] getCode() {
        return code;
    }
    public String stringCode(){
        String a = "";
        for (short i:code) {
            a+=" "+i;
        }
        return a;
    }

    public void setCode(short[] code) {
        this.code = code;
    }
}
