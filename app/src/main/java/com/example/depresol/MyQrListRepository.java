package com.example.depresol;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.depresol.Find;
import com.example.depresol.MyFind;

import java.util.List;

class MyQrListRepository {

    private static final String TAG = "MyQrListRepository";
    private MediatorLiveData<Find> getInfo = new MediatorLiveData<>();


    MutableLiveData<Boolean> isLoadingGetList = new MutableLiveData<>();

    MutableLiveData<Boolean> isLoadingRepo = new MutableLiveData<>();

    MutableLiveData<List<Find>> liveData;

    public void getData(MutableLiveData<List<Find>> liveData) {
        isLoadingGetList.setValue(true);
        try {
            isLoadingGetList.setValue(false);
            List<Find> myQrItem = MyFind.get().getData();
            liveData.postValue( myQrItem);
        }catch (Exception ex){
            isLoadingGetList.setValue(false);
        }


    }



}
