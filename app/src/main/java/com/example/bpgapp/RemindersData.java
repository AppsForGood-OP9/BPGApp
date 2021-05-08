package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "Reminders_table")
public class RemindersData implements Serializable {
    //Create ID column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Create text column
    @ColumnInfo(name = "TimeText")
    private String TimeText;

    //Add more fields later
    //Generate getter and setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTimeText() {
        return TimeText;
    }

    public void setTimeText(String text) {
        this.TimeText = text;
    }
}
