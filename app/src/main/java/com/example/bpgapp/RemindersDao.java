package com.example.bpgapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RemindersDao {
    /**
    *Insert query
     **/
    @Insert(onConflict = REPLACE)
    void insert(RemindersData ReminderData);

    /**
     * Delete query
     * @param ReminderData
     */
    @Delete
    void delete(RemindersData ReminderData);

    /**
     * Delete all query
     * @param ReminderData
     */
    @Delete
    void reset(List<RemindersData> ReminderData);

    /**
     * Update query
     * @param timeID
     * @param timeText
     */
    @Query("UPDATE Reminders_table SET time = :timeText WHERE ID = :timeID")
    void update(int timeID, String timeText);

    /**
     * Get all data query
     * @return
     */
    @Query("SELECT * FROM Reminders_table")
    List<RemindersData> getAll();
}
