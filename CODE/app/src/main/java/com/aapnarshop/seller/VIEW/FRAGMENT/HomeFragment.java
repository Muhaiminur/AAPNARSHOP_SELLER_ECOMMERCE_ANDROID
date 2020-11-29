package com.aapnarshop.seller.VIEW.FRAGMENT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEWMODEL.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Utility utility;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(HomeViewModel.class);
        utility = new Utility(getActivity());

        return root;
    }

}