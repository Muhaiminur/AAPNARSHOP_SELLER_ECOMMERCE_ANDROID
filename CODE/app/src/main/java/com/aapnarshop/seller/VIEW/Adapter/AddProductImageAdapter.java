package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.RecyclerviewAddProductBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class AddProductImageAdapter extends RecyclerView.Adapter<AddProductImageAdapter.ViewHolder> {

    private Context context;
    private List<String> imageList;
    ListInterface listInterface;

    public AddProductImageAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public AddProductImageAdapter(Context context, List<String> imageList, ListInterface listInterface) {
        this.context = context;
        this.imageList = imageList;
        this.listInterface = listInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewAddProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_add_product,parent,false);
        return new AddProductImageAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            String image = imageList.get(position);
            Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.ic_product)
                    .into(holder.binding.recyclerviewAddProductProductImage);

            holder.binding.cancel.setOnClickListener(v->{
                listInterface.listIndex(position);
                imageList.remove(image);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,imageList.size());
            });
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }



    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerviewAddProductBinding binding;

        public ViewHolder(RecyclerviewAddProductBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }

    public interface ListInterface{

        void listIndex(int index);
    }
}
