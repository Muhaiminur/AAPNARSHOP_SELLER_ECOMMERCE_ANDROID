package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.Profile;
import com.aapnarshop.seller.Model.SEND.LogIn;
import com.aapnarshop.seller.Repository.SignInRepo;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepo signInRepo;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        signInRepo = new SignInRepo(application);
    }

    public void sendLogin(LogIn logIn){
        signInRepo.sendLoginInfo(logIn);
    }

    public LiveData<API_RESPONSE> getLoginResponse(){
        return signInRepo.getLoginResponse();
    }

    public void forgetPassword(LogIn logIn){
        signInRepo.forgetPassword(logIn);
    }
    public LiveData<API_RESPONSE> getForgetPasswordResponse(){

        return signInRepo.getForgetPasswordResponse();
    }


}
