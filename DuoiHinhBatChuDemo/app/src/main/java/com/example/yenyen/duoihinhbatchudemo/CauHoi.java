package com.example.yenyen.duoihinhbatchudemo;

/**
 * Created by yenyen on 5/25/2017.
 */

class CauHoi {
    int id;
    String imagePath, description, shortAnswer, fullAnswer;

    public CauHoi() {
    }

    public CauHoi(int id, String imagePath, String description, String shortAnswer, String fullAnswer) {
        this.id = id;
        this.imagePath = imagePath;
        this.description = description;
        this.shortAnswer = shortAnswer;
        this.fullAnswer = fullAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortAnswer() {
        return shortAnswer;
    }

    public void setShortAnswer(String shortAnswer) {
        this.shortAnswer = shortAnswer;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }

    public void setFullAnswer(String fullAnswer) {
        this.fullAnswer = fullAnswer;
    }
}
