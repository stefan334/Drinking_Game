package com.example.HaiSaBem;

import java.util.ArrayList;

public class Game {
   private ArrayList<User> userArrayList;
   private ArrayList<String> intrebariArrayList;
   private Integer finished;
    private Integer started;
    private Integer playerNumber;
    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getStarted() {
        return started;
    }

    public void setStarted(Integer started) {
        this.started = started;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }


    public Game() {

        finished=0;
        started=0;
        playerNumber=0;
        userArrayList=new ArrayList<>();
        intrebariArrayList = new ArrayList<>();
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
