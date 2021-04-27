package com.example.bpgapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface bpDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(bpData bpData);

    //Delete query
    @Delete
    void delete(bpData bpData);

    //Delete all query
    @Delete
    void reset(List<bpData> bpData);

    //Update query
    @Query("UPDATE bp_table SET bpText = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //Get all data query
    @Query("SELECT * FROM bp_table")
    List<bpData> getAll();
}
