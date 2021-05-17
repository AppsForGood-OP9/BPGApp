package com.example.bpgapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
Add database entities
 */
@Database(entities = {BPData.class}, version = 1, exportSchema = false)
public abstract class BPRoomDB extends RoomDatabase {

    //Create database instance
    private static BPRoomDB bpDatabase;
    //Define database name
    private static String BP_DATABASE_NAME = "bpDatabase";

    public synchronized static BPRoomDB getInstance(Context context)  {
        //Check condition
        if (bpDatabase == null)  {
            //When database is null
            //Initialize database
            bpDatabase = Room.databaseBuilder(context.getApplicationContext(), BPRoomDB.class, BP_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return bpDatabase;
    }

    //Create DAO
    public abstract BPDao bpDao();
}
