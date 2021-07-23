package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
Define table name
 */
@Entity(tableName = "bp_table")
public class BPData implements Serializable {
    /*
    Create ID column
     */
    @PrimaryKey(autoGenerate = true)
    private int ID;

    /*
    Create text columns
     */
    @ColumnInfo(name = "systolicText")
    private String systolicText;

    @ColumnInfo(name = "diastolicText")
    private String diastolicText;

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
    Gets the systolic data text
     */
    public String getSystolicText() {
        return systolicText;
    }

    /*
    Sets the systolic data text
     */
    public void setSystolicText(String text) {
        this.systolicText = text;
    }

    /*
    Gets the diastolic data text
     */
    public String getDiastolicText() {
        return diastolicText;
    }

    /*
    Sets the diastolic data text
     */
    public void setDiastolicText(String text) {
        this.diastolicText = text;
    }

    /*
    Gets the date set by the user
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
