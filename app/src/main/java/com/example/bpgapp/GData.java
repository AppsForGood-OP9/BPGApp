package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
Define table name
 */
@Entity(tableName = "g_table")
public class GData implements Serializable {
    /*
    Create ID column
     */
    @PrimaryKey(autoGenerate = true)
    private int ID;

    /*
    Create text columns
     */
    @ColumnInfo(name = "glucoseText")
    private String glucoseText;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "notesText")
    private String notesText;

    @ColumnInfo(name = "currentDate")
    private String currentDate;

    /*
    Gets the ID
     */
    public int getID() {
        return ID;
    }

    /*
    Sets the ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /*
    Gets the glucose data text
     */
    public String getGlucoseText() {
        return glucoseText;
    }

    /*
    Sets the glucose data text
     */
    public void setGlucoseText(String text) {
        this.glucoseText = text;
    }

    /*
    Gets the date
     */
    public String getDate() {
        return date;
    }

    /*
    Sets the date
     */
    public void setDate(String text) {
        this.date = text;
    }

    /*
    Gets the time
     */
    public String getTime() {
        return time;
    }

    /*
    Sets the time
     */
    public void setTime(String text) {
        this.time = text;
    }

    /*
    Gets the notes text
     */
    public String getNotesText() {
        return notesText;
    }

    /*
    Sets the notes text
     */
    public void setNotesText(String text) {
        this.notesText = text;
    }

    /*
   Gets the current date
    */
    public String getCurrentDate() {
        return currentDate;
    }

    /*
    Sets the current date
     */
    public void setCurrentDate(String text) {
        this.currentDate = text;
    }

}
