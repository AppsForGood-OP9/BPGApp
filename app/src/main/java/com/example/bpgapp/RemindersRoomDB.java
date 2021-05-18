package com.example.bpgapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * add database entities
 */
@Database(entities = {RemindersData.class}, version = 1, exportSchema = false)
public abstract class RemindersRoomDB extends RoomDatabase {
    //Create database instance
    private static RemindersRoomDB RemindersDatabase;
    //Define database name
    private static String REMINDERS_DATABASE_NAME = "RemindersDatabase";

    /**
     * Get's instance from input context.
     * @param context
     * @return the database
     */
    public synchronized static RemindersRoomDB getInstance(Context context)  {
        //Check condition
        if (RemindersDatabase == null)  {
            //When database is null
            //Initialize database
            RemindersDatabase = Room.databaseBuilder(context.getApplicationContext(), RemindersRoomDB.class, REMINDERS_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return database
        return RemindersDatabase;
    }

    /**
     * Create Dao
     * @return
     */
    public abstract RemindersDao RemindersDao();
}
