package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.aapnarshop.seller.Repository.SignInRepo;

public class HomeViewModel extends AndroidViewModel {

    private SignInRepo signInRepo;
    private LiveData<API_RESPONSE> responseLiveData;
    private MutableLiveData<Boolean> progressbarObservable;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        signInRepo = new SignInRepo(application);
    }

}