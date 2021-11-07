package com.example.food_orderig.helper;

import androidx.lifecycle.MutableLiveData;

public class MainViewModel {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public void  setText1(String s){
        mutableLiveData.setValue(s);
    }
    public MutableLiveData<String> getText1 (){
        return mutableLiveData;
    }
}
