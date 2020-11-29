package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Repository.ProfileRepo;

import okhttp3.MultipartBody;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepo profileRepo;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepo = new ProfileRepo(application);
    }

    public void updateSellerProfileImage(MultipartBody.Part profileImage){
        profileRepo.updateSellerProfileImage(profileImage);
    }
    public LiveData<API_RESPONSE> getProfileImageUpdateResponse(){
        return profileRepo.getProfileImageUploadResponse();
    }
}
