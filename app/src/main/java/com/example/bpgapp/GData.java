package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "g_table")
public class GData implements Serializable {
    //Create ID column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Create text column
    @ColumnInfo(name = "glucoseText")
    private String glucoseText;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "notesText")
    private String notesText;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGlucoseText() {
        return glucoseText;
    }

    public void setGlucoseText(String text) {
        this.glucoseText = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String text) {
        this.date = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String text) {
        this.time = text;
    }

    public String getNotesText() {
        return notesText;
    }

    public void setNotesText(String text) {
        this.notesText = text;
    }

}
