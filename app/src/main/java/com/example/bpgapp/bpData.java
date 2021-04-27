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
        @ColumnInfo(name = "bpText")
        private String bpText;

        //Add more fields later
        //Generate getter and setter

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBpText() {
            return bpText;
        }

        public void setBpText(String text) {
            this.bpText = text;
        }

    }
