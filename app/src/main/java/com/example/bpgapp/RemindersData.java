package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "Reminders_table")
public class RemindersData implements Serializable {
    /**
     * Create ID column
     */
    @PrimaryKey(autoGenerate = true)
    private int ID;

    /**
     * Create text column
     */
    @ColumnInfo(name = "time")
    private String time;

    /**
     * getter for ID
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * setter for ID
     * @return
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter for time
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * setter for time
     * @return
     */
    public void setTime(String text) {
        this.time = text;
    }
}
