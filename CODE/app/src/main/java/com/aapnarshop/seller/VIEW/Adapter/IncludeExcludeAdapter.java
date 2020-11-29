package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Model.GET.OfferProductListResponse;
import com.aapnarshop.seller.Model.SEND.AddOfferProduct;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEWMODEL.OfferViewModel;
import com.aapnarshop.seller.databinding.RecyclerviewExcludeIncludeProductBinding;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Anik Roy on 25,November,2020
 */
public class IncludeExcludeAdapter extends RecyclerView.Adapter<IncludeExcludeAdapter.ViewHolder> {

    private Context context;
    private final List<OfferProductListResponse> offerProductListResponseList;
    String offerId;
    OfferViewModel offerViewModel;

    public IncludeExcludeAdapter(Context context, List<OfferProductListResponse> offerProductListResponseList, String offerId, OfferViewModel offerViewModel) {
        this.context = context;
        this.offerProductListResponseList = offerProductListResponseList;
        this.offerId = offerId;
        this.offerViewModel = offerViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewExcludeIncludeProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_exclude_include_product, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferProductListResponse offerProduct = offerProductListResponseList.get(position);
        Glide.with(context)
                .load(offerProduct.getProductImage())
                .placeholder(R.drawable.ic_default_image)
                .into(holder.binding.excludeIncludeIv);
        holder.binding.excludeIncludeTitle.setText(offerProduct.getProductName());
        holder.binding.excludeIncludeUnit.setText(offerProduct.getUnit());
        if (offerProduct.getStatus().equalsIgnoreCase(KeyWord.STATUS_INCLUDE)) {
            holder.binding.excludeIncludeStatus.setText(context.getResources().getString(R.string.minus));
        } else if (offerProduct.getStatus().equalsIgnoreCase(KeyWord.STATUS_EXCLUDE)) {
            holder.binding.excludeIncludeStatus.setText(context.getResources().getString(R.string.plus));
        }
        holder.binding.excludeIncludeStatus.setOnClickListener(v -> {

            if (offerProduct.getStatus().equalsIgnoreCase(KeyWord.STATUS_INCLUDE)) {
                AddOfferProduct addOfferProduct = new AddOfferProduct(offerId, offerProduct.getProductId(), KeyWord.STATUS_EXCLUDE);
                offerViewModel.addOfferProduct(addOfferProduct);
            } else if (offerProduct.getStatus().equalsIgnoreCase(KeyWord.STATUS_EXCLUDE)) {
                AddOfferProduct addOfferProduct = new AddOfferProduct(offerId, offerProduct.getProductId(), KeyWord.STATUS_INCLUDE);
                offerViewModel.addOfferProduct(addOfferProduct);
            }

        });
    }

    @Override
    public int getItemCount() {
        return offerProductListResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerviewExcludeIncludeProductBinding binding;

        public ViewHolder(RecyclerviewExcludeIncludeProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
