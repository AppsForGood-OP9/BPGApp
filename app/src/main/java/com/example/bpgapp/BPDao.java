package com.example.bpgapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BPDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(bpData bpData);

    //Delete query
    @Delete
    void delete(bpData bpData);

    //Delete all query
    @Delete
    void reset(List<bpData> bpData);

    //Update systolic
    @Query("UPDATE bp_table SET systolicText = :sText WHERE ID = :sID")
    void updateSystolic(int sID, String sText);

    //Update diastolic
    @Query("UPDATE bp_table SET diastolicText = :dText WHERE ID = :dID")
    void updateDiastolic(int dID, String dText);

    //Update notes
    @Query("UPDATE bp_table SET notesText = :nText WHERE ID = :nID")
    void updateNotes(int nID, String nText);

    //Update systolic and diastolic
    @Query("UPDATE bp_table SET systolicText = :sText, diastolicText =:dText WHERE ID = :sID")
    void updateBoth(int sID, String sText, String dText);

    //Update query with all data
    @Query("UPDATE bp_table SET systolicText = :sText, diastolicText =:dText, notesText =:nText WHERE ID =:sID")
    void updateAll(int sID, String sText, String dText, String nText);

    //Get all data query
    @Query("SELECT * FROM bp_table")
    List<bpData> getAll();

    //Delete all data
    @Query("DELETE FROM bp_table")
    void deleteAll();
}