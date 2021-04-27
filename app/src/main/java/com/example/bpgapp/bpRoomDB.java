package com.example.bpgapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Add database entities
@Database(entities = {bpData.class}, version = 1, exportSchema = false)
public abstract class bpRoomDB extends RoomDatabase {

    //Create database instance
    private static bpRoomDB bpDatabase;
    //Define database name
    private static String BP_DATABASE_NAME = "bpDatabase";

    public synchronized static bpRoomDB getInstance(Context context)  {
        //Check condition
        if (bpDatabase == null)  {
            //When database is null
            //Initialize database
            bpDatabase = Room.databaseBuilder(context.getApplicationContext(), bpRoomDB.class, BP_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Maybe use to help with null object issue?
        //Return database
        return bpDatabase;
    }

    //Create Dao
    public abstract bpDao bpDao();
}
