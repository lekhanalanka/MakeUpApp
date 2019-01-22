package com.example.makeupapp.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.makeupapp.Favourite_class;

import java.util.List;

@Dao
public interface DataAccessObject
{

    @Query("SELECT * FROM Favourite_Item")
    LiveData<List<Favourite_class>> getData();

    @Query("SELECT * FROM Favourite_Item WHERE id == :id")
    Favourite_class checkFavoriteItem(String id);

    @Insert
    void insert(Favourite_class fav_item);

    @Delete
    void delete(Favourite_class fav_item);
}
