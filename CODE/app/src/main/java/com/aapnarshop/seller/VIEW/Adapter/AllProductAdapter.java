package com.aapnarshop.seller.VIEW.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.aapnarshop.seller.Model.GET.AllProduct;
import com.aapnarshop.seller.Model.SEND.ProductQuantity;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.FRAGMENT.product.AllProductDirections;
import com.aapnarshop.seller.VIEWMODEL.ProductViewModel;
import com.aapnarshop.seller.databinding.RecyclerviewAllProductBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {

    Context context;
    private List<AllProduct> productList;
    private ProductViewModel productViewModel;
    int count = 0;

    public AllProductAdapter(Context context, List<AllProduct> products, ProductViewModel productViewModel) {
        this.context = context;
        productList = products;
        this.productViewModel = productViewModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerviewAllProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_all_product, parent, false);
        return new AllProductAdapter.ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            AllProduct product = productList.get(position);

            holder.binding.recyclerViewAllProductProductName.setText(product.getProductTitle());
            holder.binding.recyclerViewAllProductAvailableProduct.setText(product.getRemainingProduct() + " " + product.getUnit() + " " + "available");
            holder.binding.recyclerViewAllProductProductPrice.setText("৳" + " " + product.getUnitPrice());
            holder.binding.recyclerViewAllProductProductUnit.setText(" per" + " " + product.getUnit());


            //Circular Black Progress Loader for Image
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(circularProgressDrawable);
            requestOptions.error(R.drawable.ic_default_image);
            requestOptions.skipMemoryCache(true);
            requestOptions.fitCenter();


            Glide.with(context)
                    .load(product.getProductImage())
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.binding.recyclerViewAllProductProductImage);


            holder.binding.recyclerViewAllProductPlus.setOnClickListener(v -> {
                count = Integer.parseInt(holder.binding.recyclerViewAllProductCount.getText().toString()) + 1;
                holder.binding.recyclerViewAllProductCount.setSelection(holder.binding.recyclerViewAllProductCount.getText().length());
                holder.binding.recyclerViewAllProductCount.setText(String.valueOf(count));
            });
            holder.binding.recyclerViewAllProductMinusBtn.setOnClickListener(v -> {
                if (Integer.parseInt(holder.binding.recyclerViewAllProductCount.getText().toString())>0) {
                    count = Integer.parseInt(holder.binding.recyclerViewAllProductCount.getText().toString()) - 1;
                    holder.binding.recyclerViewAllProductCount.setSelection(holder.binding.recyclerViewAllProductCount.getText().length());
                    holder.binding.recyclerViewAllProductCount.setText(String.valueOf(count));
                }
                
            });

            holder.binding.recyclerViewAllProductUpdate.setOnClickListener(v -> {

                ProductQuantity productQuantity =  new ProductQuantity(product.getProductId(),holder.binding.recyclerViewAllProductCount.getText().toString());
                productViewModel.updateProductQuantity(productQuantity).observe((LifecycleOwner) context, api_response -> {

                    if (api_response.getCode()==200){

                        int remainingQuantity = Integer.parseInt(product.getRemainingProduct())+ Integer.parseInt(holder.binding.recyclerViewAllProductCount.getText().toString());
                        holder.binding.recyclerViewAllProductAvailableProduct.setText(remainingQuantity + " " + product.getUnit() + " " + "available");
                        holder.binding.recyclerViewAllProductCount.setText("0");
                    }
                });

            });

            holder.binding.recyclerViewAllProductDetails.setOnClickListener(v -> {

                AllProductDirections.ActionAllProductFragmentToEditProductFragment action = AllProductDirections.actionAllProductFragmentToEditProductFragment();
                action.setProductId(product.getProductId());
                Navigation.findNavController(v).navigate(action);
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerviewAllProductBinding binding;

        public ViewHolder(RecyclerviewAllProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
