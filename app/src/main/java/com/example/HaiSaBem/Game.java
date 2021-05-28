package com.example.HaiSaBem;

import java.util.ArrayList;

public class Game {
   private ArrayList<User> userArrayList;
   private ArrayList<String> intrebariArrayList;
    public Game() {
    }

    public Game(ArrayList<User> userArrayList, ArrayList<String> intrebariArrayList) {
        this.userArrayList = userArrayList;
        this.intrebariArrayList = intrebariArrayList;
    }



    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public ArrayList<String> getIntrebariArrayList() {
        return intrebariArrayList;
    }

    public void setIntrebariArrayList(ArrayList<String> intrebariArrayList) {
        this.intrebariArrayList = intrebariArrayList;
    }
}
