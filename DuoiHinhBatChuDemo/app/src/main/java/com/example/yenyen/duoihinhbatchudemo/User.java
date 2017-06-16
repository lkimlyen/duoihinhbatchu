package com.example.yenyen.duoihinhbatchudemo;

import java.io.Serializable;

/**
 * Created by yenyen on 6/15/2017.
 */

public class User implements Serializable{
    String id;
    String name;
    Integer score, money;

    public User() {
    }

    public User(String id, String name, Integer score, Integer money) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
