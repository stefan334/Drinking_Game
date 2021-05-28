package com.example.HaiSaBem;

import java.util.ArrayList;

public class Game {
   private ArrayList<User> userArrayList;
   private ArrayList<String> intrebariArrayList;
   private boolean finished;
   private boolean started;
   private int playerNumber;
    public Game() {
        finished=false;
        started=false;
        playerNumber=0;
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

    public void setUser(User user){
        userArrayList.add(user);
        playerNumber++;
    }

    public ArrayList<String> getIntrebariArrayList() {
        return intrebariArrayList;
    }

    public void setIntrebariArrayList(ArrayList<String> intrebariArrayList) {
        this.intrebariArrayList = intrebariArrayList;
    }
}
