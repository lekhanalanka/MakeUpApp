package com.example.makeupapp.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.makeupapp.Favourite_class;

@Database(entities = {Favourite_class.class},version = 1,exportSchema = false)

public abstract class DataBase extends RoomDatabase {

    public abstract DataAccessObject MyData();

    private static volatile DataBase INSTANCE;

    public static DataBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "makeup").allowMainThreadQueries().
                            fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
