package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Model.GET.Rating;
import com.aapnarshop.seller.Repository.RatingRepo;

public class RatingViewModel extends AndroidViewModel {

    private RatingRepo ratingRepo;

    public RatingViewModel(@NonNull Application application) {
        super(application);
        ratingRepo = new RatingRepo(application);
    }

    public LiveData<Rating> getRating(){
        return ratingRepo.getRating();
    }
}
