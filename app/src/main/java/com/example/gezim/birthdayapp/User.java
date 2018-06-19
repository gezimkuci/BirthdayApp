package com.example.gezim.birthdayapp;

/**
 * Created by Gezim on 5/22/2018.
 */

public class User {
private String name;
private String birthday;
public User(String name,String birthday)
{
    this.name=name;
    this.birthday=birthday;

}

    public String getMsgtemplate() {
        return msgtemplate;
    }

    public void setMsgtemplate(String msgtemplate) {
        this.msgtemplate = msgtemplate;
    }

    private String msgtemplate;
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
