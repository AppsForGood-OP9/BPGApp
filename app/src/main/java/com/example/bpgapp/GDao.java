package com.example.bpgapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GDao {
    /*
    Insert query
     */
    @Insert(onConflict = REPLACE)
    void insert(GData gData);

    /*
    Delete query
     */
    @Delete
    void delete(GData gData);

    /*
    Delete all query
     */
    @Delete
    void reset(List<GData> gData);

    /*
    Update glucose level data
     */
    @Query("UPDATE g_table SET glucoseText = :gText WHERE ID = :gID")
    void updateGlucose(int gID, String gText);

    /*
    Update notes
     */
    @Query("UPDATE g_table SET notesText = :nText WHERE ID = :nID")
    void updateNotes(int nID, String nText);

    /*
    Update query with all data
     */
    @Query("UPDATE g_table SET glucoseText = :gText, notesText =:nText WHERE ID =:gID")
    void updateAll(int gID, String gText, String nText);

    /*
    Get all data query
     */
    @Query("SELECT * FROM g_table")
    List<GData> getAll();

    /*
    Delete all data
     */
    @Query("DELETE FROM g_table")
    void deleteAll();
}
