package com.example.makeupapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.os.AsyncTask;

import com.example.makeupapp.DAO.DataAccessObject;
import com.example.makeupapp.DAO.DataBase;

import java.util.List;

public class Repository {

    private DataAccessObject mDataDao;
    private LiveData<List<Favourite_class>> listLiveData;

    public Repository(Application application)
    {
       DataBase db = DataBase.getDataBase(application);
        mDataDao=db.MyData();
        listLiveData=mDataDao.getData();

    }

    public LiveData<List<Favourite_class>> getmFavItem() {
        return listLiveData;
    }

    public void insert(Favourite_class fav_item)
    {
        new insertAsyncTask(mDataDao).execute(fav_item);
    }



    private  class insertAsyncTask extends AsyncTask<Favourite_class,Void,Void> {

        DataAccessObject mAsyncDao;
        public insertAsyncTask(DataAccessObject mDataDao)
        {
            mAsyncDao=mDataDao;

        }

        @Override
        protected Void doInBackground(Favourite_class... favouriteClasses) {
            mAsyncDao.insert(favouriteClasses[0]);
            return null;
        }
    }

    public void delete(Favourite_class fav_item)
    {
        new deleteAsyncTask(mDataDao).execute(fav_item);
    }
    private class deleteAsyncTask extends AsyncTask<Favourite_class,Void,Void> {

        DataAccessObject mAsyncDao1;

        public deleteAsyncTask(DataAccessObject mDataDao) {
            mAsyncDao1=mDataDao;
        }

        @Override
        protected Void doInBackground(Favourite_class... favouriteClasses) {
            mAsyncDao1.delete(favouriteClasses[0]);
            return null;
        }
    }

    public Favourite_class checkItem(String id)
    {
        Favourite_class model=mDataDao.checkFavoriteItem(id);
        return model;
    }
}
