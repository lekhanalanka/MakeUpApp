package com.example.makeupapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavModelClass extends AndroidViewModel
{
    private Repository mRepository;
    private LiveData<List<Favourite_class>> mFavitems;

    public FavModelClass(@NonNull Application application) {
        super(application);

        mRepository = new Repository(application);
        mFavitems = mRepository.getmFavItem();
    }

    public LiveData<List<Favourite_class>> getmFavitems() {
        return mFavitems;
    }
    public void insert(Favourite_class fav_items)
    {
        mRepository.insert(fav_items);
    }

    public void delete(Favourite_class fav_items)
    {
        mRepository.delete(fav_items);
    }

    public Favourite_class searchForItem(String id)
    {
        Favourite_class model=mRepository.checkItem(id);
        return model;
    }
}
