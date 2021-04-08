package edu.illinois.handsup;

/**
 * Created by ehennenfent on 12/6/2017.
 */

public class User {

    public String uid;
    public Integer drawable;
    public Boolean did_volunteer;
    public String email;
    public Integer group;
    public String name;
    public Integer score;

    public User(){

    }

    public User(String name, Integer score, String email, Integer group, Boolean did_volunteer){
        this.uid = uid;
        this.name = name;
        this.score = score;
        this.email = email;
        this.group = group;
        this.did_volunteer = did_volunteer;
    }

    public void set_supplemental(String uid, Integer drawable){
        this.uid = uid;
        this.drawable = drawable;
    }
}
