package com.example.bpgapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
Add database entities
 */
@Database(entities = {GData.class}, version = 1, exportSchema = false)
public abstract class GRoomDB extends RoomDatabase {
    //Create database instance
    private static GRoomDB gDatabase;
    //Define database name
    private static String G_DATABASE_NAME = "gDatabase";

    public synchronized static GRoomDB getInstance(Context context)  {
        //Check condition
        if (gDatabase == null)  {
            //When database is null
            //Initialize database
            gDatabase = Room.databaseBuilder(context.getApplicationContext(), GRoomDB.class, G_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return gDatabase;
    }

    //Create DAO
    public abstract GDao gDao();
}
