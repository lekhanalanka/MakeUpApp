package com.example.makeupapp;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

public class ViewModel extends android.arch.lifecycle.ViewModel {

    MutableLiveData<List<Favourite_class>> mutableLiveData;

    public MutableLiveData<List<Favourite_class>> getMutableLiveData() {
        if (mutableLiveData==null){

            mutableLiveData=new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void setList(List<Favourite_class> list) {
        mutableLiveData.setValue(list);
    }
}
