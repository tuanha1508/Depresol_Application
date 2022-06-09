package com.example.depresol;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.depresol.Find;

import java.util.List;
class MyFindViewModel extends ViewModel {

    MutableLiveData<List<Find>> mLiveDataQrList;
    private MyQrListRepository repository;

    public MyFindViewModel(MyQrListRepository repository) {
        this.repository = repository;
        mLiveDataQrList = new MutableLiveData();
    }

    public void getDataQrListVM() {

        repository.getData(mLiveDataQrList);
    }
}