package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.Settings;
import com.aapnarshop.seller.Model.SEND.ChangePassword;
import com.aapnarshop.seller.Repository.SettingsRepo;

public class SettingsViewModel extends AndroidViewModel {

    private SettingsRepo settingsRepo;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        settingsRepo = new SettingsRepo(application);
    }

    public LiveData<Settings> getSettings(){

        return settingsRepo.getSettings();
    }

    public void changePassword(ChangePassword changePassword){
        settingsRepo.changePassword(changePassword);
    }

    public LiveData<API_RESPONSE> getChangePasswordResponse(){

        return settingsRepo.getChangePasswordResponse();
    }

    public void updateSettings(Settings settings){
        settingsRepo.updateSettings(settings);
    }
}
