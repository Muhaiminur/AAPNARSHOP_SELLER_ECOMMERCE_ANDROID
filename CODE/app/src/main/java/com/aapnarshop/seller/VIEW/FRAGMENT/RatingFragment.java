package com.aapnarshop.seller.VIEW.FRAGMENT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.CommentAdapter;
import com.aapnarshop.seller.VIEWMODEL.RatingViewModel;
import com.aapnarshop.seller.databinding.FragmentRatingBinding;


public class RatingFragment extends Fragment {

    FragmentRatingBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating, container, false);
        try {
            RatingViewModel ratingViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
            Utility utility = new Utility(getActivity());
            String language = utility.getLangauge();
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(language.equals(KeyWord.BANGLA) ? KeyWord.RATING_BN : KeyWord.RATING);

            binding.ratingCategoryWise.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.average_rating_following_category_bn) : getString(R.string.average_rating_following_category));

            ratingViewModel.getRating().observe(getActivity(), rating -> {


                binding.ratingProductQuality.setText(getString(R.string.product_quality) + "(" + rating.getProductQuality() + ")");
                binding.ratingShopDeliveryService.setText(getString(R.string.shop_delivery_service) + "(" + rating.getDeliveryService() + ")");
                binding.ratingBehaviour.setText(getString(R.string.behaviour) + "(" + rating.getSellerBehavior() + ")");
                binding.ratingProductPackaging.setText(getString(R.string.product_packaging) + "(" + rating.getProductPackaging() + ")");
                binding.productQualityStar.setRating(Float.parseFloat(rating.getProductQuality()));
                binding.ratingShopDeliveryServiceStar.setRating(Float.parseFloat(rating.getDeliveryService()));
                binding.ratingBehaviourStar.setRating(Float.parseFloat(rating.getSellerBehavior()));
                binding.ratingProductPackagingStar.setRating(Float.parseFloat(rating.getProductPackaging()));
                binding.ratingTotalRating.setText("Total Rating " + rating.getTotalRating());
                binding.ratingAverage.setText("Average Rating" + " " + rating.getAverageRating() + " out of 5");

               CommentAdapter adapter=new CommentAdapter(getActivity(),rating.getCommentList());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                binding.ratingCommentRv.setLayoutManager(mLayoutManager);
                binding.ratingCommentRv.setAdapter(adapter);


            });


        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}