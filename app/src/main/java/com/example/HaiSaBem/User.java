package com.example.HaiSaBem;

import java.io.Serializable;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class User implements Serializable {
    private String ID;
    private String name;

    public User() {
        name= new String();

    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
    public boolean userExists(){
        if(!name.equals(""))
            return TRUE;
        return FALSE;
    }
}
