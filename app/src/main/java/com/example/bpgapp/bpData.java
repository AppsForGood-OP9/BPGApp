package com.example.bpgapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "bp_table")
public class bpData implements Serializable {

        //Create ID column
        @PrimaryKey(autoGenerate = true)
        private int ID;

        //Create text column
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

        //Add more fields later
        //Generate getter and setter

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSystolicText() {
            return systolicText;
        }

        public void setSystolicText(String text) {
            this.systolicText = text;
        }

        public String getDiastolicText() {
            return diastolicText;
        }

        public void setDiastolicText(String text) {
            this.diastolicText = text;
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

        //Might have to get older tables cleared out
        //Remove other values
        //Might have schema problem
        //Completely delete the app


}
