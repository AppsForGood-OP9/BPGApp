package com.example.bpgapp;

public class TableModelBP {
    private String date;
    private String time;
    private String systolic;
    private String diastolic;
    private String notes;
    //private String currentDate;

    public TableModelBP(String date, String time, String systolic, String diastolic, String notes)  {
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.notes = notes;
        //this.currentDate = currentDate;
    }

    public String getDate()  {
        return date;
    }

    /*
    public String getCurrentDate()  {
        return currentDate;
    }
*/
    public String getTime()  {
        return time;
    }

    public String getSystolic()  {
        return systolic;
    }

    public String getDiastolic()  {
        return diastolic;
    }

    public String getNotes()  {
        return notes;
    }

}
