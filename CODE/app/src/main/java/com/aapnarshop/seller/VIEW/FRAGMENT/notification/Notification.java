package com.aapnarshop.seller.VIEW.FRAGMENT.notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentNotificationBinding;

public class Notification extends Fragment {

    FragmentNotificationBinding binding;
    private Utility utility;
    private String language;

    public Notification() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification, container, false);
        try{
            utility = new Utility(getActivity());
            language = utility.getLangauge();
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView toolbarHeader = (TextView) toolbar.findViewById(R.id.title);
            toolbarHeader.setText(language.equals(KeyWord.BANGLA)?KeyWord.NOTIFICATION_BN:getActivity().getResources().getString(R.string.notification));
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }
}