package com.example.bpgapp;

public class TableModelG {
    String date;
    String time;
    String glucose;
    String notes;

    public TableModelG(String date, String time, String glucose, String notes)  {
        this.date = date;
        this.time = time;
        this.glucose = glucose;
        this.notes = notes;
    }

    public String getDate()  {
        return date;
    }

    public String getTime()  {
        return time;
    }

    public String getGlucose()  {
        return glucose;
    }

    public String getNotes()  {
        return notes;
    }
}
