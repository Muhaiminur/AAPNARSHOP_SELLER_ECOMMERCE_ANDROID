package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Model.GET.Category;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.ItemSubPopupBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> categoryList;
    private final Context context;
    private CategoryInterface categoryInterface;
    ListPopupWindow popupWindow;

    public CategoryAdapter(List<Category> categoryList, Context context, CategoryInterface categoryInterface, ListPopupWindow popup) {
        this.categoryList = categoryList;
        this.context = context;
        this.categoryInterface = categoryInterface;
        popupWindow = popup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubPopupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_sub_popup,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.binding.popupMenuTitle.setText(category.getCategoryTitle());
        holder.itemView.setOnClickListener(v -> {
            categoryInterface.categoryId(category.getCategoryId());
            popupWindow.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSubPopupBinding binding;

        public ViewHolder(ItemSubPopupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CategoryInterface{
        void categoryId(String categoryId);
    }
}
