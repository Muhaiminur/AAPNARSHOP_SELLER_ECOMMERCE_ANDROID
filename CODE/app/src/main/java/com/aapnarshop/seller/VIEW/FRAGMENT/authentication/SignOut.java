package com.aapnarshop.seller.VIEW.FRAGMENT.authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentSignOutBinding;

import org.jetbrains.annotations.NotNull;

public class SignOut extends Fragment {

    FragmentSignOutBinding binding;

    public SignOut() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_out, container, false);
        try {
            String args = SignOutArgs.fromBundle(getArguments()).getMessage();
            binding.signOutMessage.setText(args);

            binding.signOutBackBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signOutFragment_to_signUpFragment));
            binding.signOut.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signOutFragment_to_signInFragment));
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }
}