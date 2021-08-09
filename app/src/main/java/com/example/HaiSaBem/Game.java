package com.example.HaiSaBem;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
   private User player1;
   private int questionNr;
   private String id;


    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPlayer3() {
        return player3;
    }

    public void setPlayer3(User player3) {
        this.player3 = player3;
    }

    public User getPlayer4() {
        return player4;
    }

    public void setPlayer4(User player4) {
        this.player4 = player4;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public void setQuestionNr(int questionNr) {
        this.questionNr = questionNr;
    }

    private User player2;
   private User player3;
   private User player4;
   private ArrayList<String> intrebariArrayList;
   private Integer finished;
    private Long started;
    private Integer playerNumber;
    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Long getStarted() {
        return started;
    }

    public void setStarted(Long started) {
        this.started = started;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }
    public void incrementPlayerNumber(){
        playerNumber++;
    }


    public Game() {
        questionNr=0;
        finished=0;
        started= 0L;
        playerNumber=0;
        player1= new User();
        player2= new User();
        player3= new User();
        player4= new User();
        intrebariArrayList = new ArrayList<>();
    }

    public Game(ArrayList<User> userArrayList, ArrayList<String> intrebariArrayList) {

        this.intrebariArrayList = intrebariArrayList;
    }







    public ArrayList<String> getIntrebariArrayList() {
        return intrebariArrayList;
    }

    public void setIntrebariArrayList(ArrayList<String> intrebariArrayList) {
        this.intrebariArrayList = intrebariArrayList;
    }


    public void setQuestionNrLong(Long questionNr) {
        this.questionNr = questionNr.intValue();
    }
}
