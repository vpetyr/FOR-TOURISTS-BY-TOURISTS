package com.example.fortouristsbytourists;

/**
 * describing the user object
 */
public class User {

    private String fName, sName, email, pass;

    public User(){}
    public User(String _fName, String _sName, String _email, String _pass)

    {

        fName = _fName;
        sName = _sName;
        email = _email;
        pass = _pass;

    }

    public String getfName() { return fName; }

    public void setfName(String fName) { this.fName = fName; }

    public String getsName() { return sName; }

    public void setsName(String sName) { this.sName = sName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPass() { return pass; }

    public void setPass(String pass) { this.pass = pass; }

}