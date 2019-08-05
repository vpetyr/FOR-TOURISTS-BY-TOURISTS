package com.example.fortouristsbytourists;

public class MyException extends Exception {
    private String msg;
    MyException() { msg = null; }
    MyException(String str) { msg = str; }
    public String toString() {
        return "Exception: (" + msg + ")";
    }
}